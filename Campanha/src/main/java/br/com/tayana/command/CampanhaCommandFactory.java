package br.com.tayana.command;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CampanhaCommandFactory implements ApplicationContextAware {
	
	 private ApplicationContext applicationContext;

	    public <CMD extends CampanhaLogicCommand> CMD createInstance(Class<CMD> commandClazz) {
	        return applicationContext.getBean(commandClazz);
	    }

	    @Override
	    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	        this.applicationContext = applicationContext;
	    }
}