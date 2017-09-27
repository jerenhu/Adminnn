package com.ecnice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrivilegeTest {
	

	@Before
	public  void setUpBeforeClass()  {
		try {
			@SuppressWarnings("resource")
			ApplicationContext cxt = new ClassPathXmlApplicationContext(
					new String[] { "file:src/main/resources/config/spring_common.xml"});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() throws Exception {
		/*Client client = jaxWsClientFactoryService.createClient("http://datawebservice.ecnu.edu.cn/yaxiawebservice.asmx?wsdl");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String[] param  = new String[]{"test","00004857",time,"7D7C4A98-B7FD-4DA2-BDA2-CE1FC9B3B48F"};
		Object[] result = jaxWsClientFactoryService.getDynamicInvkeWithClient(client, "http://datawebservice.ecnu.edu.cn/yaxiawebservice.asmx", "UserAuthticationByAccountPwdDB", param);
		System.out.println(result);*/
	}
}
