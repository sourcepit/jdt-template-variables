
package org.sourcepit.jdt.template.variables;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Bernd Vogt <bernd.vogt@sourcepit.org>
 */
public class Activator implements BundleActivator
{
   private static BundleContext context;

   public static BundleContext getContext()
   {
      return context;
   }

   public void start(BundleContext bundleContext) throws Exception
   {
      Activator.context = bundleContext;
   }

   public void stop(BundleContext bundleContext) throws Exception
   {
      Activator.context = null;
   }

}
