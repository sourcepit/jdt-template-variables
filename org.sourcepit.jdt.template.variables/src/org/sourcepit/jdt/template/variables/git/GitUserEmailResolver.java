/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.jdt.template.variables.git;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.UserConfig;

/**
 * @author Bernd Vogt <bernd.vogt@sourcepit.org>
 */
public class GitUserEmailResolver extends AbstractGitTemplateVariableResolver<UserConfig>
{
   public GitUserEmailResolver()
   {
      super("git_user_email",
         "The user email that is configured in the current Git repository or in your global Git configuration.",
         UserConfig.KEY);
   }

   @Override
   protected String resolve(TemplateContext context, Repository repository, UserConfig userConfig)
   {
      return userConfig.getAuthorEmail();
   }
}
