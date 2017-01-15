package it.infocamere.cont2.reportv2.application;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import it.infocamere.cont2.reportv2.filters.CustomSiteMeshFilter;

@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }    
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
       // builder.serializationInclusion(JsonInclude.Include.NON_NULL);
       // builder.serializationInclusion(Include.NON_EMPTY);
        //i campi non annotati sono inseriti indipendendentemente dalla jsonView usata
        builder.featuresToEnable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        Hibernate4Module hm = new Hibernate4Module();
        //se non esplicitamente inizializzati,gli oggetti lazy load appaiono null
        hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, false);
       // builder.modulesToInstall(Hibernate4Module.class);
        builder.modulesToInstall(hm);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
    
//    @Bean
//    public Jackson2ObjectMapperBuilder configureObjectMapper() {
//        return new Jackson2ObjectMapperBuilder()
//            .modulesToInstall(Hibernate4Module.class);
//    }
    
    @Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CustomSiteMeshFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
    

	
}