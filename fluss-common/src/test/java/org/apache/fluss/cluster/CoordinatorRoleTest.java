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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/** Test for {@link CoordinatorRole}. */
public class CoordinatorRoleTest {

    @Test
    void testLeaderRole() {
        CoordinatorRole role = CoordinatorRole.LEADER;
        assertThat(role.getRoleId()).isEqualTo(0);
        assertThat(role).isEqualTo(CoordinatorRole.LEADER);
    }

    @Test
    void testStandbyRole() {
        CoordinatorRole role = CoordinatorRole.STANDBY;
        assertThat(role.getRoleId()).isEqualTo(1);
        assertThat(role).isEqualTo(CoordinatorRole.STANDBY);
    }

    @Test
    void testUnknownRole() {
        CoordinatorRole role = CoordinatorRole.UNKNOWN;
        assertThat(role.getRoleId()).isEqualTo(-1);
        assertThat(role).isEqualTo(CoordinatorRole.UNKNOWN);
    }

    @ParameterizedTest
    @EnumSource(CoordinatorRole.class)
    void testAllRolesHaveValidIds(CoordinatorRole role) {
        int roleId = role.getRoleId();
        assertThat(roleId).isIn(0, 1, -1);
    }

    @Test
    void testFromRoleIdForLeader() {
        CoordinatorRole role = CoordinatorRole.fromRoleId(0);
        assertThat(role).isEqualTo(CoordinatorRole.LEADER);
    }

    @Test
    void testFromRoleIdForStandby() {
        CoordinatorRole role = CoordinatorRole.fromRoleId(1);
        assertThat(role).isEqualTo(CoordinatorRole.STANDBY);
    }

    @Test
    void testFromRoleIdForUnknown() {
        CoordinatorRole role = CoordinatorRole.fromRoleId(-1);
        assertThat(role).isEqualTo(CoordinatorRole.UNKNOWN);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 10, 100, -2, -100})
    void testFromRoleIdForUnrecognizedIds(int unrecognizedId) {
        CoordinatorRole role = CoordinatorRole.fromRoleId(unrecognizedId);
        assertThat(role).isEqualTo(CoordinatorRole.UNKNOWN);
    }

    @Test
    void testRoleIdRoundTrip() {
        CoordinatorRole[] roles = {CoordinatorRole.LEADER, CoordinatorRole.STANDBY};

        for (CoordinatorRole originalRole : roles) {
            int roleId = originalRole.getRoleId();
            CoordinatorRole restoredRole = CoordinatorRole.fromRoleId(roleId);
            assertThat(restoredRole)
                    .as("Role should be restored from its ID")
                    .isEqualTo(originalRole);
        }
    }

    @Test
    void testRoleComparison() {
        assertThat(CoordinatorRole.LEADER).isNotEqualTo(CoordinatorRole.STANDBY);
        assertThat(CoordinatorRole.LEADER).isNotEqualTo(CoordinatorRole.UNKNOWN);
        assertThat(CoordinatorRole.STANDBY).isNotEqualTo(CoordinatorRole.UNKNOWN);
    }

    @Test
    void testRoleHashCode() {
        assertThat(CoordinatorRole.LEADER.hashCode())
                .isNotEqualTo(CoordinatorRole.STANDBY.hashCode());
        assertThat(CoordinatorRole.LEADER.hashCode())
                .isNotEqualTo(CoordinatorRole.UNKNOWN.hashCode());
    }

    @Test
    void testRoleToString() {
        assertThat(CoordinatorRole.LEADER.toString()).isEqualTo("LEADER");
        assertThat(CoordinatorRole.STANDBY.toString()).isEqualTo("STANDBY");
        assertThat(CoordinatorRole.UNKNOWN.toString()).isEqualTo("UNKNOWN");
    }

    @Test
    void testRoleOrdinal() {
        assertThat(CoordinatorRole.LEADER.ordinal()).isZero();
        assertThat(CoordinatorRole.STANDBY.ordinal()).isEqualTo(1);
        assertThat(CoordinatorRole.UNKNOWN.ordinal()).isEqualTo(2);
    }

    @Test
    void testAllRolesAreDefined() {
        CoordinatorRole[] roles = CoordinatorRole.values();
        assertThat(roles).hasSize(3);
        assertThat(roles)
                .contains(CoordinatorRole.LEADER, CoordinatorRole.STANDBY, CoordinatorRole.UNKNOWN);
    }

    @Test
    void testValueOf() {
        assertThat(CoordinatorRole.valueOf("LEADER")).isEqualTo(CoordinatorRole.LEADER);
        assertThat(CoordinatorRole.valueOf("STANDBY")).isEqualTo(CoordinatorRole.STANDBY);
        assertThat(CoordinatorRole.valueOf("UNKNOWN")).isEqualTo(CoordinatorRole.UNKNOWN);
    }
}
