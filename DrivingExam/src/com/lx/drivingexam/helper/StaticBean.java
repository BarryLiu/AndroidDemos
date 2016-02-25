package com.lx.drivingexam.helper;
import com.lx.drivingexam.R;
public class StaticBean {
//	public static List<QuestionBean> qList=new ArrayList<QuestionBean>();
//	public static List<Bundle> bList=new ArrayList<Bundle>();
	public static final String TABLE_NAME="web_note";
	public static final String TABLE_NAME_COLLECT="collect";
	public static final String TABLE_NAME_WRONGS="wrongs";
	/**收藏表 ：收藏的Id*/
	public static final String COLLECT_TEST_ID="test_id";
	/**收藏表 ：车型*/
	public static final String COLLECT_CAR_TYPE="car_type";
	/**收藏表 ：科目类型*/
	public static final String COLLECT_KEMU_TYPE="kemu_type";
	/**收藏表 ：是否收藏 */
	public static final String IS_COLLECT="is_collect";
	/**状态 列*/
	public static final String STOCKS_TYPE="stocks_type";
	/**选择的Id */
	public static final String SELECT_ID="select_id";
	public static final String IS_SHOW_EXPTION="is_show_exption";
	/**模拟考试答题是否正确，是否答题 0:未做,1:正确,2:错误*/
	public static final String IS_MOCK_EXAMINTION="is_mock_answer";
	//////////////////////////web_note表的列。。。。。开始
    public static final String[] WEB_NOTE_COLUMNS=new String[]{"An1","An2","An3","An4","AnswerTrue",
    	"explain","sinaimg","LicenseType","Question","video_url"
    };
    public static final String WEB_NOTE_ID="_id";
    public static final String WEB_NOTE_KEMU="Kemu";
    public static final String WEB_NOTE_TYPE="Type";
    /**难度*/
    public static final String DIFF_DEGREE="diff_degree";
    ///////////////////////////web_note表的列。。。。。结束
    public static final String ANSWER_TRUE="AnswerTrue";
    public static final String[] TOOLS_COLLECT_TEXT=new String[]{"收藏本题","取消收藏"};
    public static final int[] TOOLS_COLLECT=new int[]{R.drawable.lianxi_collect,R.drawable.lianxi_collect_on};
    public static final String[] TOOLS_EXPTION_TEXT=new String[]{"本题解释","收起解释"};
    public static final int[] TOOLS_EXPTION=new int[]{R.drawable.lianxi_exption,R.drawable.lianxi_exption_on};
    /**2选项A,B*/
    public static final String[] ANS0=new String[]{"A","B"};
    /**4选项A,B,C,D*/
    public static final String[] ANS1=new String[]{"A","B","C","D"};
//    /**当前题目数据*/
//    public static Bundle bundle;
//    /**当前题目题号*/
//    public static int position=0;
//    /**题目总数*/
//    public static int listSize=0;
//    /**collect_id: 是否收藏 0没有收藏,1收藏
//     * exption_id: 是否显示答案0不显示,1显示
//     * **/
//    public static int collect_id=0,exption_id=0;
//    /**练习类型0:顺序练习,1:随机练习,2:模拟考试,3:收藏,4:错题**/
//    public static int PRACTISE_TYPE=0;
//    /**是否显示 解释*/
//    public static boolean is_static_exption =true;
//    /**是否显示 判断*/
//    public static boolean is_static_judge =true;
//    /**科目0:科目一,1:科目二，:科目三,3:科目四**/ 
//    public static int KEMU=0;
}
