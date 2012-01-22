/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.jdt.template.variables.git;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.UserConfig;

/**
 * @author Bernd
 */
public class GitUserNameResolver extends AbstractGitTemplateVariableResolver<UserConfig>
{
   public GitUserNameResolver()
   {
      super("git_user_name",
         "The user name that is configured in the current Git repository or in your global Git configuration.",
         UserConfig.KEY);
   }

   @Override
   protected String resolve(TemplateContext context, Repository repository, UserConfig userConfig)
   {
      return userConfig.getAuthorName();
   }
}
