/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.jdt.template.variables.git;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

/**
 * @author Bernd
 */
public abstract class AbstractGitTemplateVariableResolver<T> extends TemplateVariableResolver
{
   protected final SectionParser<T> sectionParser;

   protected AbstractGitTemplateVariableResolver(String type, String description, SectionParser<T> sectionParser)
   {
      super(type, description);
      this.sectionParser = sectionParser;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected String resolve(TemplateContext context)
   {
      final Repository repository = findRepository(context);
      if (repository == null)
      {
         return null;
      }
      return resolve(context, repository);
   }

   protected String resolve(TemplateContext context, Repository repository)
   {
      final T configModel = repository.getConfig().get(sectionParser);
      if (configModel != null)
      {
         return resolve(context, repository, configModel);
      }
      return null;
   }

   protected abstract String resolve(TemplateContext context, Repository repository, T configModel);

   protected Repository findRepository(TemplateContext context)
   {
      final String projectName = context.getVariable("project_name");
      if (projectName != null)
      {
         final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
         if (project.isAccessible())
         {
            final File projectDir = project.getLocation().toFile();
            return findRepository(projectDir);
         }
      }
      return null;
   }

   protected Repository findRepository(File projectDir)
   {
      try
      {
         return new RepositoryBuilder().findGitDir(projectDir).build();
      }
      catch (Exception e)
      {
         try
         {
            return new RepositoryBuilder().setWorkTree(projectDir).build();
         }
         catch (Exception e1)
         {
            return null;
         }
      }
   }
}
