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

package org.apache.fluss.cluster;

import org.apache.fluss.annotation.PublicEvolving;

import javax.annotation.Nullable;

/**
 * The role of a coordinator server in the Fluss cluster.
 *
 * @since 0.7
 */
@PublicEvolving
public enum CoordinatorRole {
    /** The leader coordinator server. */
    LEADER(0),

    /** A standby coordinator server. */
    STANDBY(1),

    /** Unknown coordinator role. */
    UNKNOWN(-1);

    private final int roleId;

    CoordinatorRole(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Get the role ID of this coordinator role.
     *
     * @return the role ID
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Get the CoordinatorRole from its role ID.
     *
     * @param roleId the role ID
     * @return the corresponding CoordinatorRole, or UNKNOWN if the role ID is not recognized
     */
    @Nullable
    public static CoordinatorRole fromRoleId(int roleId) {
        if (roleId == LEADER.roleId) {
            return LEADER;
        } else if (roleId == STANDBY.roleId) {
            return STANDBY;
        } else {
            return UNKNOWN;
        }
    }
}
