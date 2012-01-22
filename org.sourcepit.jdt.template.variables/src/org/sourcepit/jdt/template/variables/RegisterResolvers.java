
package org.sourcepit.jdt.template.variables;

import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.sourcepit.jdt.template.variables.git.GitUserEmailResolver;
import org.sourcepit.jdt.template.variables.git.GitUserNameResolver;

/**
 * Currently it's not possible to provide more variables for <tt>java-code-templates</tt>, we can only add more
 * <tt>editor-templates</tt> via internal extension-point.
 * 
 * @author hoehmann
 * @author Bernd
 */
@SuppressWarnings("restriction")
public class RegisterResolvers implements IStartup
{
   private static final String JAVA_PLUGIN_ID = "org.eclipse.jdt.ui"; //$NON-NLS-1$

   public void earlyStartup()
   {
      // check if plug-in org.eclipse.jdt.ui is already active
      final Bundle bundle = Platform.getBundle(JAVA_PLUGIN_ID);
      if (bundle != null && bundle.getState() == Bundle.ACTIVE)
      {
         // register resolvers
         registerResolvers();
      }
      else
      {
         // register listener to get informed, when plug-in becomes active
         final BundleContext bundleContext = Activator.getContext().getBundle().getBundleContext();
         bundleContext.addBundleListener(new BundleListener()
         {
            public void bundleChanged(final BundleEvent pEvent)
            {
               final Bundle bundle2 = pEvent.getBundle();
               if (!bundle2.getSymbolicName().equals(JAVA_PLUGIN_ID))
               {
                  return;
               }
               if (bundle2.getState() == Bundle.ACTIVE)
               {
                  registerResolvers();
                  bundleContext.removeBundleListener(this);
               }
            }
         });
      }
   }

   /*
    * Internal method to register resolvers with all context types.
    */
   private void registerResolvers()
   {
      final ContextTypeRegistry codeTemplateContextRegistry = JavaPlugin.getDefault().getCodeTemplateContextRegistry();
      @SuppressWarnings("unchecked")
      final Iterator<TemplateContextType> ctIter = codeTemplateContextRegistry.contextTypes();
      while (ctIter.hasNext())
      {
         final TemplateContextType contextType = ctIter.next();
         contextType.addResolver(new GitUserNameResolver());
         contextType.addResolver(new GitUserEmailResolver());
      }
   }
}
