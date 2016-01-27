package io.barryliu.facebud.res;

import io.barryliu.facebud.R;


public class MyRes {
	public static final String TAG_ISBOY="isBoy";
	public static final String TAG_TYPE = "image_type";

	
	static int [] boy_default;
	public int[] getBoyDefault() {
		if(boy_default == null){
			boy_default = new int[]{
					R.drawable.pic_rs1_29, 	//Ĭ�J���
					R.drawable.pic_s2_20006,//Ĭ�JĘ��
					R.drawable.pic_s3_1,   //Ĭ�Jüë
					R.drawable.pic_s4_34,	//Ĭ�J�۾�
					R.drawable.pic_s5_59,	//Ĭ�J���
					R.drawable.show_nothing,//Ĭ�J����----�o
					R.drawable.show_nothing,//Ĭ�J���R----�o
					R.drawable.pic_s8_1,	//Ĭ�J�·�
					R.drawable.show_nothing,//Ĭ�Jñ��----�o
					R.drawable.show_nothing,//Ĭ�J�ۺ�----�o
					R.drawable.pic_s11_26,	//Ĭ�J����----�{ɫ
					R.drawable.show_nothing	//Ĭ�J���� ----�o	
			};
		}
		return boy_default;
	}

	public int[] getGirlDefault() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
