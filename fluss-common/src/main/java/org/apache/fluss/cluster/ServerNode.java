package org.apache.fluss.cluster;

import org.apache.fluss.annotation.PublicEvolving;

import javax.annotation.Nullable;

import java.util.Objects;

/**
 * Information about a Fluss server node.
 *
 * @since 0.6
 */
@PublicEvolving
public class ServerNode {
    private final int id;
    private final String uid;
    private final String host;
    private final int port;
    private final ServerType serverType;

    /** rack info for ServerNode. Currently, only tabletServer has rack info. */
    private final @Nullable String rack;

    /** The role of the coordinator server. Only set for COORDINATOR server type. */
    private final @Nullable CoordinatorRole coordinatorRole;

    /** Flag indicating whether the coordinator server is live. */
    private final boolean isCoordinatorLive;

    // Cache hashCode as it is called in performance sensitive parts of the code (e.g.
    // RecordAccumulator.ready)
    private Integer hash;

    public ServerNode(int id, String host, int port, ServerType serverType) {
        this(id, host, port, serverType, null, null, false);
    }

    public ServerNode(int id, String host, int port, ServerType serverType, @Nullable String rack) {
        this(id, host, port, serverType, rack, null, false);
    }

    public ServerNode(
            int id,
            String host,
            int port,
            ServerType serverType,
            @Nullable String rack,
            @Nullable CoordinatorRole coordinatorRole,
            boolean isCoordinatorLive) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.serverType = serverType;
        this.rack = rack;
        this.coordinatorRole = coordinatorRole;
        this.isCoordinatorLive = isCoordinatorLive;

        if (coordinatorRole != null && serverType != ServerType.COORDINATOR) {
            throw new IllegalArgumentException(
                    "coordinatorRole can only be set for COORDINATOR server type");
        }

        if (serverType == ServerType.COORDINATOR) {
            this.uid = "cs-" + id;
        } else {
            this.uid = "ts-" + id;
        }
    }

    /**
     * The node id of this node. Note: coordinator server may have conflict node id with tablet
     * server.
     */
    public int id() {
        return id;
    }

    /**
     * Unique id of server node in the cluster. It distinguishes same node id of coordinator server
     * and tablet server with different string prefix.
     */
    public String uid() {
        return uid;
    }

    /** The host name for this node. */
    public String host() {
        return host;
    }

    /** The port for this node. */
    public int port() {
        return port;
    }

    /** The server type of this node. */
    public ServerType serverType() {
        return serverType;
    }

    /** The rack for this node. */
    public @Nullable String rack() {
        return rack;
    }

    /**
     * The role of this coordinator server.
     *
     * @return the coordinator role, or null if not a coordinator server
     */
    public @Nullable CoordinatorRole coordinatorRole() {
        return coordinatorRole;
    }

    /**
     * Whether this coordinator server is live.
     *
     * @return true if the coordinator server is live, false otherwise
     */
    public boolean isCoordinatorLive() {
        return isCoordinatorLive;
    }

    /**
     * Check whether this node is empty, which may be the case if noNode() is used as a placeholder
     * in a response payload with an error.
     *
     * @return true if it is, false otherwise
     */
    public boolean isEmpty() {
        return host == null || host.isEmpty() || port < 0;
    }

    @Override
    public int hashCode() {
        Integer h = this.hash;
        if (h == null) {
            int result = 31 + ((host == null) ? 0 : host.hashCode());
            result = 31 * result + id;
            result = 31 * result + port;
            result = 31 * result + serverType.hashCode();
            result = 31 * result + ((rack == null) ? 0 : rack.hashCode());
            result = 31 * result + ((coordinatorRole == null) ? 0 : coordinatorRole.hashCode());
            result = 31 * result + (isCoordinatorLive ? 1 : 0);
            this.hash = result;
            return result;
        } else {
            return h;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ServerNode other = (ServerNode) obj;
        return id == other.id
                && port == other.port
                && Objects.equals(host, other.host)
                && serverType == other.serverType
                && Objects.equals(rack, other.rack)
                && coordinatorRole == other.coordinatorRole
                && isCoordinatorLive == other.isCoordinatorLive;
    }

    @Override
    public String toString() {
        return host
                + ":"
                + port
                + " (id: "
                + uid
                + ", rack: "
                + rack
                + ", coordinator_role: "
                + coordinatorRole
                + ", is_live: "
                + isCoordinatorLive
                + ")";
    }
}
