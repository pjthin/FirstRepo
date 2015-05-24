package fr.pjthin.root.spring.startAndRunArgs;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class RunWithParams {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Must fill parameters!");
            System.exit(1);
        }

        // Create beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // Define a bean and register it
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Arrays.class, "asList")
                .addConstructorArgValue(args).getBeanDefinition();
        beanFactory.registerBeanDefinition("args", beanDefinition);

        // Create first context (with args)
        GenericApplicationContext cmdArgCxt = new GenericApplicationContext(beanFactory);
        // Must call refresh to initialize context
        cmdArgCxt.refresh();

        // Create application context, passing command line context as parent
        ApplicationContext mainContext = new ClassPathXmlApplicationContext(
                new String[] { "spring/runWithParams.xml" }, cmdArgCxt);

        // See if it's in the context
        System.out.println("Args: " + mainContext.getBean("args"));
        System.out.println("MainArgs: " + ToStringBuilder.reflectionToString(mainContext.getBean("mainArgs")));

        // Other way is to create bean properties and use it in xml with #{mainArgsBuildProgrammatically.good} and
        // #{mainArgsBuildProgrammatically.text}
        // BeanDefinition mainArgsDefinition = BeanDefinitionBuilder.rootBeanDefinition(MainArgs.class)
        // .addPropertyReference("good", args[0]).addPropertyValue("text", args[1]).getBeanDefinition();
        // beanFactory.registerBeanDefinition("mainArgsBuildProgrammatically", mainArgsDefinition);

    }

}
