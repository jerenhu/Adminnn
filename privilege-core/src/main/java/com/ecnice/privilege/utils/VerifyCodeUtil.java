package com.ecnice.privilege.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ecnice.privilege.constant.PrivilegeConstant;

/** 
 * 验证码图片生成器 
 */
public class VerifyCodeUtil {  
    /** 
     * 验证码图片的宽度。 
     */  
    private int width = 110;  
    /** 
     * 验证码图片的高度。 
     */  
    private int height = 44;  
    /** 
     * 验证码的数量。 
     */  
    private Random random = new Random();  
    
    public VerifyCodeUtil(){}  
    /** 
     * 生成随机颜色 
     * @param fc    前景色 
     * @param bc    背景色 
     * @return  Color对象，此Color对象是RGB形式的。 
     */  
    public Color getRandomColor(int fc, int bc) {  
        if (fc > 255)  
            fc = 200;  
        if (bc > 255)  
            bc = 255;  
        int r = fc + random.nextInt(bc - fc);  
        int g = fc + random.nextInt(bc - fc);  
        int b = fc + random.nextInt(bc - fc);  
        return new Color(r, g, b);  
    }  
      
    /** 
     * 绘制干扰线 
     * @param g Graphics2D对象，用来绘制图像 
     * @param nums  干扰线的条数 
     */  
    public void drawRandomLines(Graphics2D g ,int nums ){  
        g.setColor(this.getRandomColor(160, 200)) ;  
        for(int i=0 ; i<nums ; i++){  
            int x1 = random.nextInt(width) ;  
            int y1 = random.nextInt(height);  
            int x2 = random.nextInt(12) ;  
            int y2 = random.nextInt(12) ;  
            g.drawLine(x1, y1, x2, y2) ;  
        }  
    }  
      
    /** 
     * 获取随机字符串， 
     *      此函数可以产生由大小写字母，汉字，数字组成的字符串 
     * @param length    随机字符串的长度 
     * @return  随机字符串 
     */  
    public String drawRandomString(int length , Graphics2D g){  
        StringBuffer strbuf = new StringBuffer() ;  
        String temp = "" ;  
        int itmp = 0 ;  
        for(int i=0 ; i<length ; i++){  
            switch(random.nextInt(3)){
//        	 switch(1){
            case 1:     //生成A～Z的字母  
                itmp = random.nextInt(26) + 65 ;
                itmp = itmp==79 ? 65 : itmp;
                temp = String.valueOf((char)itmp);  
                break;  
            case 2:  
                itmp = random.nextInt(26) + 97 ;  
                temp = String.valueOf((char)itmp);  
            default:  
                itmp = random.nextInt(9) + 49 ;  
                temp = String.valueOf((char)itmp) ;  
                break;  
            }  
            Color color = new Color(20+random.nextInt(20) , 20+random.nextInt(20) ,20+random.nextInt(20) );  
            g.setColor(color);  
            //想文字旋转一定的角度  
            AffineTransform trans = new AffineTransform();  
            trans.rotate(random.nextInt(45)*3.14/180, 15*i+8, 7) ;  
            //缩放文字  
            float scaleSize = random.nextFloat() + 0.8f ;  
            if(scaleSize>1f)  
                scaleSize = 1f ;  
            trans.scale(scaleSize, scaleSize) ;  
            g.setTransform(trans) ;  
            g.drawString(temp, 15*i+20, 20) ;  
            // 设置字体
            String[] FONT_TYPES_ZH = { /*"宋体",*/ "Arial" };
            String[] fontTypes = FONT_TYPES_ZH;
            int fontTypesLen = fontTypes.length;
            Random rand = new Random(System.currentTimeMillis());
            g.setFont(new Font(fontTypes[rand.nextInt(fontTypesLen)], Font.BOLD, 24));  
            strbuf.append(temp) ;  
        }  
        g.dispose() ;  
        return strbuf.toString() ;  
    }
    
    public static byte[] getVerifyCode(int width,int height,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	VerifyCodeUtil idCode = new VerifyCodeUtil();  
        BufferedImage image =new BufferedImage(width , height , BufferedImage.TYPE_INT_BGR) ;  
        Graphics2D g = image.createGraphics() ;  
        //定义字体样式  
        Font myFont = new Font("微软雅黑" , Font.BOLD , 24) ;  
        //设置字体  
        g.setFont(myFont) ;  
        g.setColor(idCode.getRandomColor(100 , 250)) ;  
        //绘制背景  
        g.fillRect(10, 10, idCode.getWidth() , idCode.getHeight()) ;  
        g.setColor(idCode.getRandomColor(100, 200)) ;  
        idCode.drawRandomLines(g, 200) ;  
        //随机验证码
        String verifyCode = idCode.drawRandomString(4, g) ;
        if(StringUtils.isNotBlank(verifyCode)) {
        	//把验证码放入cookie里面
        	CookiesUtil.put(response, PrivilegeConstant.VERIFY_CODE, verifyCode);
        }
        g.dispose() ;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageOutputStream out = ImageIO.createImageOutputStream(output);
        ImageIO.write(image, "JPEG", out);
        out.close();
        return output.toByteArray();
    }
    
    public int getWidth() {  
        return width;  
    }  
    public void setWidth(int width) {  
        this.width = width;  
    }  
    public int getHeight() {  
        return height;  
    }  
    public void setHeight(int height) {  
        this.height = height;  
    }  
}     