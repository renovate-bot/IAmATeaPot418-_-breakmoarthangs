/*-
 * #%L
 * Maven JGitFlow Plugin
 * %%
 * Copyright (C) 2017 Atlassian Pty, LTD, Ultreia.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import com.atlassian.jgitflow.core.JGitFlow
import com.atlassian.maven.plugins.jgitflow.it.FinishScriptHelper

try
{
    helper = new FinishScriptHelper(basedir, localRepositoryPath, context)
    flow = JGitFlow.getOrInit(basedir)
    flow.git().checkout().setName("master").call()

    helper.comparePomFiles("expected-master-pom.xml", "pom.xml")

    flow.git().checkout().setName("develop").call()

    helper.comparePomFiles("expected-develop-pom.xml", "pom.xml")
}
catch (Exception e)
{
    System.err.println(e.getMessage())
    return false;
}
