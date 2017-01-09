package it.infocamere.cont2.reportv2.filters;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
          // Assigning default decorator if no path specific decorator found
            builder.addDecoratorPath("/*", "/WEB-INF/pages/decorators/main.jsp")
           // Map decorators to specific path patterns. 
     // .addDecoratorPath("/login", "/WEB-INF/sitemesh/simpleDecorator .jsp")
       // Exclude few paths from decoration.
      .addExcludedPath("/javadoc/*")                
      .addExcludedPath("/brochures/*");
    }
}
