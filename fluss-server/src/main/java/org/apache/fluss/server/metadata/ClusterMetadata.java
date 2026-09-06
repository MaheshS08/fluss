/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.fluss.server.metadata;

import org.apache.fluss.annotation.VisibleForTesting;
import org.apache.fluss.cluster.CoordinatorRole;
import org.apache.fluss.rpc.messages.MetadataResponse;
import org.apache.fluss.rpc.messages.UpdateMetadataRequest;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This entity used to describe the cluster metadata, including coordinator server address, alive
 * tablets servers and {@link TableMetadata} list or {@link PartitionMetadata}, which can be used to
 * build {@link MetadataResponse} or convert from {@link UpdateMetadataRequest}.
 */
public class ClusterMetadata {

    private final @Nullable ServerInfo coordinatorServer;
    private final List<ServerInfo> allCoordinators;
    private final Map<Integer, CoordinatorRole> coordinatorRoles;
    private final Set<Integer> liveCoordinatorIds;
    private final Set<ServerInfo> aliveTabletServers;
    private final List<TableMetadata> tableMetadataList;
    private final List<PartitionMetadata> partitionMetadataList;

    @VisibleForTesting
    public ClusterMetadata(
            @Nullable ServerInfo coordinatorServer, Set<ServerInfo> aliveTabletServers) {
        this(
                coordinatorServer,
                Collections.emptyList(),
                Collections.emptyMap(),
                Collections.emptySet(),
                aliveTabletServers,
                Collections.emptyList(),
                Collections.emptyList());
    }

    public ClusterMetadata(
            @Nullable ServerInfo coordinatorServer,
            Set<ServerInfo> aliveTabletServers,
            List<TableMetadata> tableMetadataList,
            List<PartitionMetadata> partitionMetadataList) {
        this(
                coordinatorServer,
                Collections.emptyList(),
                Collections.emptyMap(),
                Collections.emptySet(),
                aliveTabletServers,
                tableMetadataList,
                partitionMetadataList);
    }

    public ClusterMetadata(
            @Nullable ServerInfo coordinatorServer,
            List<ServerInfo> allCoordinators,
            Map<Integer, CoordinatorRole> coordinatorRoles,
            Set<Integer> liveCoordinatorIds,
            Set<ServerInfo> aliveTabletServers,
            List<TableMetadata> tableMetadataList,
            List<PartitionMetadata> partitionMetadataList) {
        this.coordinatorServer = coordinatorServer;
        this.allCoordinators = allCoordinators;
        this.coordinatorRoles = coordinatorRoles;
        this.liveCoordinatorIds = liveCoordinatorIds;
        this.aliveTabletServers = aliveTabletServers;
        this.tableMetadataList = tableMetadataList;
        this.partitionMetadataList = partitionMetadataList;
    }

    public @Nullable ServerInfo getCoordinatorServer() {
        return coordinatorServer;
    }

    public List<ServerInfo> getAllCoordinators() {
        return allCoordinators;
    }

    public Map<Integer, CoordinatorRole> getCoordinatorRoles() {
        return coordinatorRoles;
    }

    public Set<Integer> getLiveCoordinatorIds() {
        return liveCoordinatorIds;
    }

    public Set<ServerInfo> getAliveTabletServers() {
        return aliveTabletServers;
    }

    public List<TableMetadata> getTableMetadataList() {
        return tableMetadataList;
    }

    public List<PartitionMetadata> getPartitionMetadataList() {
        return partitionMetadataList;
    }
}
