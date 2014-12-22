/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.fortress.core.ant;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used by {@link FortressAntTask} to load {@link org.apache.directory.fortress.core.rbac.AdminRole}s used to drive {@link org.apache.directory.fortress.core.DelAdminMgr#deleteRole(org.apache.directory.fortress.core.rbac.AdminRole)}.
 * It is not intended to be callable by programs outside of the Ant load utility.  The class name itself maps to the xml tag used by load utility.
 * <p>The class name, 'Deladminrole', is used for the xml tag in the load script.</p>
 * <pre>
 * {@code
 * <target name="all">
 *     <FortressAdmin>
 *         <deladminrole>
 *           ...
 *         </deladminrole>
 *     </FortressAdmin>
 * </target>
 * }
 * </pre>
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class Deladminrole
{
	final private List<AdminRoleAnt> roles = new ArrayList<>();

    /**
     * All Ant data entities must have a default constructor.
     */
	public Deladminrole() { }


    /**
     * <p>This method name, 'addRole', is used for the derived xml tag 'role' in the load script.</p>
     * <pre>
     * {@code
     * <deladminrole>
     *      <role name="DemoAdminUsers"
     * </deladminrole>
     * }
     * </pre>
     *
     * @param role contains extension of {@link org.apache.directory.fortress.core.rbac.AdminRole}.
     */
	public void addRole(AdminRoleAnt role)
	{
		this.roles.add(role);
	}

    /**
     * Used by {@link FortressAntTask#deleteAdminRoles()} to retrieve list of AdminRoles as defined in input xml file.
     * @return collection containing {@link AdminRoleAnt}s targeted for removal.
     */
	public List<AdminRoleAnt> getRoles()
	{
		return this.roles;
	}
}

