package com.dragonit96.funnyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dragonit96.funnyquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "funnyQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context){
        if (instance == null){
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    //Tạo DataBase
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS "+
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NB + " INTERGER," +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionTable(){
        Question q1 = new Question("Có cổ nhưng không có miệng là gì?",
                "A","B","C",1,Question.DIFFICULTY_MID);
        insertQuestion(q1);
        Question q2 = new Question("Medium: Đáp án đúng là B",
                "Cái áo","Cái chai","Ấm nước",1,Question.DIFFICULTY_MID);
        insertQuestion(q2);
        Question q3 = new Question("Tôi luôn mang giày đi ngủ. Tôi là ai?",
                "Con rết","Con ngựa","Con gà",2,Question.DIFFICULTY_MID);
        insertQuestion(q3);
        Question q4 = new Question("Bạn làm việc gì đầu tiên mỗi buổi sáng?",
                "Mở mắt","Đánh răng","Ăn sáng",1,Question.DIFFICULTY_MID);
        insertQuestion(q4);
        Question q5 = new Question("Tôi chu du khắp thế giới mà tôi vẫn ở nguyên một chỗ, tôi là ai?",
                "Con tàu","Máy bay","Con tem",3,Question.DIFFICULTY_MID);
        insertQuestion(q5);
        Question q6 = new Question("Đố bạn có bao nhiêu chữ C trong câu sau đây: “ Cơm, canh, cháo gì tớ cũng thích ăn!”",
                "1","2","4",1,Question.DIFFICULTY_MID);
        insertQuestion(q6);
        Question q7 = new Question("Cái gì người mua biết, người bán biết, người xài không bao giờ biết?”",
                "Quà bí mật","Quan tài","Thẻ cào điện thoại",2,Question.DIFFICULTY_MID);
        insertQuestion(q7);

        Question q8 = new Question("Bạn đang ở trong một cuộc đua và bạn vừa vượt qua người thứ nhì . Vậy bây giờ bạn đang ở vị trí nào trong đoàn đua ấy?”",
                "Nhất","Nhì","Không ở vị trí nào",2,Question.DIFFICULTY_MID);
        insertQuestion(q8);

        Question q9 = new Question("Cái gì hút được cả ánh sáng?”",
                "Hồ lô của Thái Thượng Lão Quân","Lòng người","Hố đen vũ trụ",3,Question.DIFFICULTY_MID);
        insertQuestion(q9);

        Question q10 = new Question("Bảng chữ cái tiếng Việt bắt đầu từ chữ nào?”",
                "A","B","C",2,Question.DIFFICULTY_MID);
        insertQuestion(q10);


        Question q11 = new Question("'Samsung' trong tiếng Hàn Quốc có nghĩa?”",
                "Phát triển","Chất lượng","Tam Sao",3,Question.DIFFICULTY_HARD);
        insertQuestion(q11);
        Question q12 = new Question("Hãng Apple được đổi tên từ Apple Computer vào năm?",
                "2005","2006","2007",3,Question.DIFFICULTY_HARD);
        insertQuestion(q12);
        Question q13 = new Question("Nguồn gốc của tên gọi Asus, công ty máy tính Đài Loan, là gì?",
                "Một loài vật trong thần thoại Hy Lạp","Tên một vị thần","Tên của người sáng lập",1,Question.DIFFICULTY_HARD);
        insertQuestion(q13);
        Question q14 = new Question("Động vật có vú nào lớn nhất thế giới?",
                "Voi","Cá voi xanh","Tinh tinh",2,Question.DIFFICULTY_HARD);
        insertQuestion(q14);
        Question q15 = new Question("Sân vận động lớn nhất thế giới nằm ở đâu?",
                "Brazil","Trung Quốc","Triều Tiên",3,Question.DIFFICULTY_HARD);
        insertQuestion(q15);
        Question q16 = new Question("Đâu là thư viện lớn nhất thế giới?",
                "Thư viện Hoàng gia Anh","Thư viện Quốc hội Mỹ","Thư viện trường Stanford",2,Question.DIFFICULTY_HARD);
        insertQuestion(q16);
        Question q17 = new Question("Hành tinh nào nóng nhất hệ mặt trời?",
                "Sao Thổ","Sao Hỏa","Sao Kim",3,Question.DIFFICULTY_HARD);
        insertQuestion(q17);
        Question q18 = new Question("Hành tinh nào gần mặt trời nhất",
                "Sao Thủy","Sao Hỏa","Sao Thổ",1,Question.DIFFICULTY_HARD);
        insertQuestion(q18);
        Question q19 = new Question("Ai là tổng thống đầu tiên của Mỹ?",
                "George Washington","John Adams","Abraham Lincoln",1,Question.DIFFICULTY_HARD);
        insertQuestion(q19);


        Question q20 = new Question("Bên trái đường có một căn nhà xanh, bên phải đường có một căn nhà đỏ. Vậy, nhà trắng ở đâu?",
                "Giữa 2 nhà","Mỹ","Cạnh nhà đỏ",2,Question.DIFFICULTY_EASY);
        insertQuestion(q20);
        Question q21 = new Question("Cái gì đánh cha, đánh má, đánh anh, đánh chị, đánh em chúng ta?",
                "Bàn chải đánh răng","Cây gậy","Cảnh sát",1,Question.DIFFICULTY_EASY);
        insertQuestion(q21);
        Question q22 = new Question("Sở thú bị cháy, con gì chạy ra đầu tiên?",
                "Con Khỉ","Con Báo","Con người",3,Question.DIFFICULTY_EASY);
        insertQuestion(q22);
        Question q23 = new Question("Con trai có gì quý nhất?",
                "Của quý","Tiền","Ngọc trai",3,Question.DIFFICULTY_EASY);
        insertQuestion(q23);
        Question q24 = new Question("Tìm điểm sai trong câu: 'Dưới ánh nắng sương long lanh triệu cành hồng khoe sắc thắm'",
                "Khoe sắc khóe","Long lanh","Triệu",1,Question.DIFFICULTY_EASY);
        insertQuestion(q24);
        Question q25 = new Question("Khi Beckham thực hiện quả đá phạt đền, anh ta sẽ sút vào đâu?",
                "Quả bóng","Khung thành","Trọng tài",1,Question.DIFFICULTY_EASY);
        insertQuestion(q25);
        Question q26 = new Question("2 người: 1 lớn, 1 bé đi lên đỉnh một quả núi. Người bé là con của người lớn, nhưng người lớn lại không phải cha của người bé, hỏi người lớn là ai?",
                "Bố nuôi","Mẹ","Kẻ bắt cóc",2,Question.DIFFICULTY_EASY);
        insertQuestion(q26);
        Question q27 = new Question("Trò gì 30 người đàn ông và 2 người đàn bà đánh nhau tán loạn?",
                "Đánh trận giả","Devils may cry","Cờ vua",3,Question.DIFFICULTY_EASY);
        insertQuestion(q27);
        Question q28 = new Question("Loại nước giải khát nào chứa sắt và canxi?",
                "Cafe","Coca cola","Rồng đỏ",1,Question.DIFFICULTY_EASY);
        insertQuestion(q28);
        Question q29 = new Question("Từ gì mà 100% nguời dân Việt Nam đều phát âm sai?",
                "Sai","Lên","Đúng",1,Question.DIFFICULTY_EASY);
        insertQuestion(q29);




    }

    public void addQuestion(Question question){
        db = getWritableDatabase();
        insertQuestion(question);
    }
    public void addquestions(List<Question> questions){
        db = getWritableDatabase();

        for (Question question : questions){
            insertQuestion(question);
        }
    }
    private void insertQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NB,question.getAnswerNb());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY,question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }

    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME,null);

        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNb(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NB)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestion(String difficulty){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?" ,selectionArgs);

        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNb(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NB)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
