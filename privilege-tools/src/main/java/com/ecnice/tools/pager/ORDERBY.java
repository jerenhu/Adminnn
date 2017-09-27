package com.ecnice.tools.pager;

/**
 * @Description 排序的enum
 * @author bruce.liu
 * @time 2015年1月24日 上午10:26:01浙江蘑菇加电子商务有限公司版权所有
 */
public enum ORDERBY {
	 desc, asc;
    public ORDERBY reverse() {
      return (this == asc) ? desc : asc;
    }
}
