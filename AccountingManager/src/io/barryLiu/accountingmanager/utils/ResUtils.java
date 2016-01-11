package io.barryLiu.accountingmanager.utils;

import io.barryLiu.accountingmanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResUtils {
	public static String[] payTypeShouru = { "补助津贴", "工资", "礼金收入" };
	public static String[] payTypeZhichu = { "保健品", "报刊杂志", "车辆维护", "房屋物业",
			"服饰", "化妆品", "零食", "礼品礼金", "美容", "违章罚款", "香烟", "学杂费", "医药费", };
	public static String[] payTypes = { "保健品", "报刊杂志", "车辆维护", "房屋物业", "服饰",
			"化妆品", "零食", "礼品礼金", "美容", "违章罚款", "香烟", "学杂费", "医药费", "补助津贴",
			"工资", "礼金收入" };
	public static int[] payTypeIcons = { R.drawable.baojianpin,
			R.drawable.baokanzazhi, R.drawable.cheliangweihu,
			R.drawable.fangzuwuye, R.drawable.fushi, R.drawable.huazhuangpin,
			R.drawable.lingshi, R.drawable.lipinlijin, R.drawable.meirong,
			R.drawable.weizhangfakuan, R.drawable.xiangyan,
			R.drawable.xuezafei, R.drawable.yiliaofei, R.drawable.buzhujingtie,
			R.drawable.gongzi, R.drawable.lijinshouru };
	public static Map<String, Integer> payTypeMaps;

	public static int getPayTypeIcon(String payType) {
		if (payTypeMaps == null) {
			payTypeMaps = new HashMap<String, Integer>();
			for (int i = 0; i < payTypes.length; i++) {
				payTypeMaps.put(payTypes[i], payTypeIcons[i]);
			}
		}
		if (payTypeMaps.containsKey(payType))
			return payTypeMaps.get(payType);
		else
			return R.drawable.baojianpin;
	}

	private static List<Map<String, Object>> menus;

	public static List<Map<String, Object>> getMenusData() {
		if (menus == null) {
			menus = new ArrayList<Map<String, Object>>();
			Map<String, Object> curMap = null;
			
			curMap = new HashMap<String, Object>();
			curMap.put("name", "用户");
			curMap.put("icon", R.drawable.user);
			menus.add(curMap);
			curMap = new HashMap<String, Object>();
			curMap.put("name", "流水");
			curMap.put("icon", R.drawable.daylist_normal);
			menus.add(curMap);
			curMap = new HashMap<String, Object>();
			curMap.put("name", "图表");
			curMap.put("icon", R.drawable.chart2_normal);
			menus.add(curMap);
			curMap = new HashMap<String, Object>();
			curMap.put("name", "预算");
			curMap.put("icon", R.drawable.butget_normal);
			menus.add(curMap);
			curMap = new HashMap<String, Object>();
			curMap.put("name", "其他");
			curMap.put("icon", R.drawable.setup_normal);
			menus.add(curMap);
		}
		
		return menus;
	}
}
