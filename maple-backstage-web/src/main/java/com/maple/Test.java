/**@category
 * @author yrf
 * @date 2019年1月2日
 */
package com.maple;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.openhtmltopdf.java2d.api.DefaultPageProcessor;
import com.openhtmltopdf.java2d.api.FSPageOutputStreamSupplier;
import com.openhtmltopdf.java2d.api.Java2DRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;

/**
 * @author yrf
 * @date 2019年1月2日
 */
public class Test {

	
	public static void main(String[] args) throws Exception {
		
		DefaultPageProcessor processor = new DefaultPageProcessor(new FSPageOutputStreamSupplier() {
			@Override
			public OutputStream supply(int zeroBasedPageNumber) throws IOException {
				return new FileOutputStream("E:\\wap\\test.png");
			}
		}, BufferedImage.TYPE_3BYTE_BGR, "png");
		
		Graphics2D g2d = processor.createLayoutGraphics();
		
		Java2DRendererBuilder builder = new Java2DRendererBuilder();
		builder.withUri("file:///C:/Users/Core/Desktop/ss.html");
		builder.useLayoutGraphics(g2d);
		builder.useSVGDrawer(new BatikSVGDrawer()); // Optional
		builder.toPageProcessor(processor);
		builder.runPaged();

		g2d.dispose();
	}
}
