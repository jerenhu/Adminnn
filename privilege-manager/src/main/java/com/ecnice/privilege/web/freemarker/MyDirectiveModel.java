package com.ecnice.privilege.web.freemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class MyDirectiveModel implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map map, TemplateModel[] model,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if(body!=null) {
			body.render(env.getOut());
			//env.getOut().write(fdateStr);输出变量值
		}
	}

}
