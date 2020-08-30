package com.teamscorpion.youtubealarm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

import android.app.NotificationManager;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.ALARM_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Clock#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Clock extends Fragment implements popupActivity.OnInputSelected{
    ImageView sticker, divider1, divider2, divider3;
    Bitmap morning, afternoon, evening, night;
    Bitmap morning_line, afternoon_line, evening_line, night_line;
    Bitmap morning_first, afternoon_first, evening_first, night_first;
    Drawable[] btn_options_id, btn_add_id;
    ConstraintLayout main_layout;
    TextView Message, Date_id, Time_id;
    String[] quotes;
    int random_no;
    String Name, Date, Time;
    IntentFilter s_intentFilter;
    public  Switch sw_one, sw_two, sw_three, sw_four;
    TextView alarm_status;
    int fav_hr_1, fav_hr_2, fav_hr_3, fav_hr_4;
    int fav_min_1, fav_min_2, fav_min_3, fav_min_4;
    String[] kw;
    Context globalContext;

    Button edit_alarm;

    final Calendar[] rightNow = {Calendar.getInstance()};
    final int[] currentHourIn24Format = {rightNow[0].get(Calendar.HOUR)};
    final int[] currentMinute = {rightNow[0].get(Calendar.MINUTE)};
    final int[] currentSecond = {rightNow[0].get(Calendar.SECOND)};
    final int[] currentDay = {rightNow[0].get(Calendar.DAY_OF_WEEK)};
    final int[] currentDate = {rightNow[0].get(Calendar.DAY_OF_MONTH)};
    final int[] currentMonth = {rightNow[0].get(Calendar.MONTH)};
    final int[] currentYear = {rightNow[0].get(Calendar.YEAR)};

    final int MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 1;
    final int MY_PERMISSIONS_REQUEST_INTERNET = 2;
    final int MY_PERMISSIONS_USE_FULL_SCREEN_INTENT = 3;
    final int MY_PERMISSIONS_WAKE_LOCK = 4;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Clock() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clock.
     */
    // TODO: Rename and change types and number of parameters
    public static Clock newInstance(String param1, String param2) {
        Clock fragment = new Clock();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globalContext = this.getContext();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_clock, container, false);

        requestPermissions();

        sticker = view.findViewById(R.id.sticker);
        main_layout = view.findViewById(R.id.main_layout);
        Message = view.findViewById(R.id.textView_message);
        divider1 = view.findViewById(R.id.divider1);
        divider2 = view.findViewById(R.id.divider2);
        divider3 = view.findViewById(R.id.divider3);
        Date_id = view.findViewById(R.id.textView_date);
        Time_id = view.findViewById(R.id.textView_time);
        sw_one = view.findViewById(R.id.switch1);
        sw_two = view.findViewById(R.id.switch2);
        sw_three = view.findViewById(R.id.switch3);
        sw_four = view.findViewById(R.id.switch4);
        alarm_status = view.findViewById(R.id.alarm_status);
        edit_alarm = view.findViewById(R.id.btn_edit);

        final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
        kw = new String[5];
        kw[0] = clockSettings.getString("KeyWord1", "");
        kw[1] = clockSettings.getString("KeyWord2", "");
        kw[2] = clockSettings.getString("KeyWord3", "");
        kw[3] = clockSettings.getString("KeyWord4", "");
        kw[4] = clockSettings.getString("KeyWord5", "");

        s_intentFilter = new IntentFilter();
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        rightNow[0] = Calendar.getInstance();
        currentHourIn24Format[0] = rightNow[0].get(Calendar.HOUR_OF_DAY);
        currentMinute[0] = rightNow[0].get(Calendar.MINUTE);
        currentSecond[0] = rightNow[0].get(Calendar.SECOND);
        Date = find_date(Arrays.toString(currentDate).replaceAll("\\[|\\]|,|\\s", "")) + "/" + find_month(Arrays.toString(currentMonth).replaceAll("\\[|\\]|,|\\s", "")) + "/" + Arrays.toString(currentYear).replaceAll("\\[|\\]|,|\\s", "") + ", " + find_day(Arrays.toString(currentDay).replaceAll("\\[|\\]|,|\\s", ""));

        if(currentHourIn24Format[0] < 10 && currentMinute[0] < 10){
            Time = "0" + currentHourIn24Format[0] + " : " + "0" + currentMinute[0];
        }
        else if(currentHourIn24Format[0] < 10 && currentMinute[0] > 9){
            Time = "0" + currentHourIn24Format[0] + " : " + currentMinute[0];
        }else if(currentHourIn24Format[0] > 9 && currentMinute[0] < 10){
            Time = currentHourIn24Format[0] + " : " + "0" + currentMinute[0];
        }else{
            Time = currentHourIn24Format[0] + " : " + currentMinute[0];
        }

        Random r = new Random();
        random_no = r.nextInt(164);

        btn_options_id = new Drawable[4];
        btn_options_id[0] = ResourcesCompat.getDrawable(getResources(), R.drawable.m_settings_btn, null);
        btn_options_id[1] = ResourcesCompat.getDrawable(getResources(), R.drawable.a_settings_btn, null);
        btn_options_id[2] = ResourcesCompat.getDrawable(getResources(), R.drawable.e_settings_btn, null);
        btn_options_id[3] = ResourcesCompat.getDrawable(getResources(), R.drawable.n_settings_btn, null);

        btn_add_id = new Drawable[4];
        btn_add_id[0] = ResourcesCompat.getDrawable(getResources(), R.drawable.m_add_btn, null);
        btn_add_id[1] = ResourcesCompat.getDrawable(getResources(), R.drawable.a_add_btn, null);
        btn_add_id[2] = ResourcesCompat.getDrawable(getResources(), R.drawable.e_add_btn, null);
        btn_add_id[3] = ResourcesCompat.getDrawable(getResources(), R.drawable.n_add_btn, null);

        quotes = new String[164];
        quotes[0] = "Learn to value yourself, which means: fight for your happiness.";
        quotes[1] = "The true secret of happiness lies in the taking a genuine interest in all the details of daily life.";
        quotes[2] = "The greatest happiness you can have is knowing that you do not necessarily require happiness.";
        quotes[3] = "People should find happiness in the little things, like family.";
        quotes[4] = "As people spin faster and faster in the pursuit of merely personal happiness, they become exhausted in the futile effort of chasing themselves.";
        quotes[5] = "All happiness or unhappiness solely depends upon the quality of the object to which we are attached by love.";
        quotes[6] = "Happiness consists more in conveniences of pleasure that occur everyday than in great pieces of good fortune that happen but seldom.";
        quotes[7] = "Happiness is not an ideal of reason, but of imagination.";
        quotes[8] = "To be without some of the things you want is an indispensable part of happiness.";
        quotes[9] = "There is some kind of a sweet innocence in being human—in not having to be just happy or just sad—in the nature of being able to be both broken and whole, at the same time.";
        quotes[10] = "Even a happy life cannot be without a measure of darkness, and the word happy would lose its meaning if it were not balanced by sadness. It is far better to take things as they come along with patience and equanimity.";
        quotes[11] = "There is no happiness like that of being loved by your fellow creatures, and feeling that your presence is an addition to their comfort.";
        quotes[12] = "If you want happiness for an hour, take a nap. If you want happiness for a day, go fishing. If you want happiness for a year, inherit a fortune. If you want happiness for a lifetime, help someone else.";
        quotes[13] = "The only way to find true happiness is to risk being completely cut open";
        quotes[14] = "Nobody really cares if you’re miserable, so you might as well be happy.";
        quotes[15] = "It isn’t what you have or who you are or where you are or what you are doing that makes you happy or unhappy. It is what you think about it.";
        quotes[16] = "People are unhappy when they get something too easily. You have to sweat – that’s the only moral they know.";
        quotes[17] = "We can’t control the world. We can only (barely) control our own reactions to it. Happiness is largely a choice, not a right or entitlement.";
        quotes[18] = "Happiness cannot be traveled to, owned, earned, worn or consumed. Happiness is the spiritual experience of living every minute with love, grace, and gratitude.";
        quotes[19] = "Happiness is being content with what you have, living in freedom and liberty, having a good family life and good friends.";
        quotes[20] = "Happiness is the interval between periods of unhappiness.";
        quotes[21] = "The world is full of people looking for spectacular happiness while they snub contentment.";
        quotes[22] = "Happiness grows at our own firesides, and is not to be picked in strangers’ gardens.";
        quotes[23] = "Happiness is excitement that has found a settling down place. But there is always a little corner that keeps flapping around.";
        quotes[24] = "If only we’d stop trying to be happy we could have a pretty good time.";
        quotes[25] = "The essence of philosophy is that a man should so live that his happiness shall depend as little as possible on external things.";
        quotes[26] = "The search for happiness is one of the chief sources of unhappiness.";
        quotes[27] = "My family didn’t have a lot of money, and I’m grateful for that. Money is the longest route to happiness.";
        quotes[28] = "Happiness lies in the joy of achievement and the thrill of creative effort.";
        quotes[29] = "We tend to forget that happiness doesn’t come as a result of getting something we don’t have, but rather of recognizing and appreciating what we do have.";
        quotes[30] = "There can be no happiness if the things we believe in are different from the things we do.";
        quotes[31] = "Happiness is a direction, not a place.";
        quotes[32] = "Happiness depends upon ourselves.";
        quotes[33] = "A great obstacle to happiness is to expect too much happiness.";
        quotes[34] = "It is not how much we have, but how much we enjoy, that makes happiness.";
        quotes[35] = "Happiness is not something ready made. It comes from your own actions.";
        quotes[36] = "I think the key to life is just being a happy person, and happiness will bring you success.";
        quotes[37] = "Happiness is always the serendipitous result of looking for something else.";
        quotes[38] = "Happiness is not a goal; it is a by-product.";
        quotes[39] = "Happiness is a place between too much and too little.";
        quotes[40] = "Give a man health and a course to steer, and he’ll never stop to trouble about whether he’s happy or not.";
        quotes[41] = "Happiness is having a large, loving, caring, close-knit family in another city.";
        quotes[42] = "There is only one happiness in this life, to love and be loved.";
        quotes[43] = "How simple it is to see that we can only be happy now, and there will never be a time when it is not now.";
        quotes[44] = "No medicine cures what happiness cannot.";
        quotes[45] = "Cheerfulness is what greases the axles of the world. Don’t go through life creaking.";
        quotes[46] = "Happiness and sadness run parallel to each other. When one takes a rest, the other one tends to take up the slack.";
        quotes[47] = "True happiness is not attained through self-gratification, but through fidelity to a worthy purpose.";
        quotes[48] = "Happiness is a form of courage.";
        quotes[49] = "All happiness depends on courage and work.";
        quotes[50] = "Now and then it’s good to pause in our pursuit of happiness and just be happy.";
        quotes[51] = "So we shall let the reader answer this question for himself: who is the happier man, he who has braved the storm of life and lived or he who has stayed securely on shore and merely existed?";
        quotes[52] = "Happiness is a matter of one’s most ordinary and everyday mode of consciousness being busy and lively and unconcerned with self.";
        quotes[53] = "Happiness is distraction from the human tragedy.";
        quotes[54] = "Real happiness is not of temporary enjoyment, but is so interwoven with the future that it blesses for ever.";
        quotes[55] = "Real happiness is cheap enough, yet how dearly we pay for its counterfeit.";
        quotes[56] = "The foolish man seeks happiness in the distance, the wise grows it under his feet.";
        quotes[57] = "I must learn to be content with being happier than I deserve.";
        quotes[58] = "Who is the happiest of men? He who values the merits of others, and in their pleasure takes joy, even as though it were his own.";
        quotes[59] = "Ask yourself whether you are happy and you cease to be so.";
        quotes[60] = "Happiness is where we find it, but very rarely where we seek it.";
        quotes[61] = "You cannot protect yourself from sadness without protecting yourself from happiness.";
        quotes[62] = "Three grand essentials to happiness in this life are something to do, something to love, and something to hope for.";
        quotes[63] = "The happiness which is lacking makes one think even the happiness one has unbearable.";
        quotes[64] = "It’s been my experience that you can nearly always enjoy things if you make up your mind firmly that you will.";
        quotes[65] = "Happiness is only real when shared.";
        quotes[66] = "If you look to others for fulfillment, you will never be fulfilled. If your happiness depends on money, you will never be happy with yourself. Be content with what you have; rejoice in the way things are. When you realize there is nothing lacking, the world belongs to you.";
        quotes[67] = "You can’t be happy unless you’re unhappy sometimes.";
        quotes[68] = "In order to have great happiness you have to have great pain and unhappiness – otherwise how would you know when you’re happy?";
        quotes[69] = "There are two things to aim at in life: first, to get what you want; and after that, to enjoy it. Only the wisest of mankind achieve the second.";
        quotes[70] = "There is no cosmetic for beauty like happiness.";
        quotes[71] = "Let us be grateful to people who make us happy, they are the charming gardeners who make our souls blossom.";
        quotes[72] = "He who lives in harmony with himself lives in harmony with the universe.";
        quotes[73] = "The happiness of your life depends upon the quality of your thoughts.";
        quotes[74] = "Happiness is not a state to arrive at, but a manner of traveling.";
        quotes[75] = "Happiness is a constant work-in-progress, because solving problems is a constant work-in-progress – the solutions to today’s problems will lay the foundation for tomorrow’s problems, and so on. True happiness occurs only when you find the problems you enjoy having and enjoy solving.";
        quotes[76] = "Happiness is when what you think, what you say, and what you do are in harmony.";
        quotes[77] = "In my life I’ve learned that true happiness comes from giving. Helping others along the way makes you evaluate who you are. I think that love is what we’re all searching for. I haven’t come across anyone who didn’t become a better person through love.";
        quotes[78] = "Happiness is a well-balanced combination of love, labour, and luck.";
        quotes[79] = "Happiness always looks small while you hold it in your hands, but let it go, and you learn at once how big and precious it is.";
        quotes[80] = "Ups and downs. Victories and defeats. Sadness and happiness. That’s the best kind of life.";
        quotes[81] = "Sanity and happiness are an impossible combination.";
        quotes[82] = "Many things can make you miserable for weeks; few can bring you a whole day of happiness.";
        quotes[83] = "Happiness in this world, when it comes, comes incidentally. Make it the object of pursuit, and it leads us a wild-goose chase, and is never attained.";
        quotes[84] = "Happiness is the default state. It’s what’s there when you remove the sense that something is missing in life.";
        quotes[85] = "Those who can laugh without cause have either found the true meaning of happiness or have gone stark raving mad.";
        quotes[86] = "Happiness is holding someone in your arms and knowing you hold the whole world.";
        quotes[87] = "Happiness is the resultant of the relative strengths of positive and negative feelings rather than an absolute amount of one or the other.";
        quotes[88] = "Happiness is an accident of nature, a beautiful and flawless aberration.";
        quotes[89] = "Happiness is the natural flower of duty.";
        quotes[90] = "It’s the moments that I stopped just to be, rather than do, that have given me true happiness.";
        quotes[91] = "Happiness is a conscious choice, not an automatic response.";
        quotes[92] = "Most people would rather be certain they’re miserable, than risk being happy.";
        quotes[93] = "To be happy, you must fancy that everything you have is a gift, and you the chosen, though you worked your tail off for every bit of it.";
        quotes[94] = "Love is that condition in which the happiness of another person is essential to your own.";
        quotes[95] = "Don’t waste your time in anger, regrets, worries, and grudges. Life is too short to be unhappy.";
        quotes[96] = "The happiness of life is made up of the little charities of a kiss or smile, a kind look, a heartfelt compliment.";
        quotes[97] = "Sometimes life knocks you on your ass… get up, get up, get up!!! Happiness is not the absence of problems, it’s the ability to deal with them.";
        quotes[98] = "Happiness is not being pained in body or troubled in mind.";
        quotes[99] = "Happiness makes up in height for what it lacks in length.";
        quotes[100] = "The secret of happiness is to find a congenial monotony.";
        quotes[101] = "The greatest happiness of life is the conviction that we are loved; loved for ourselves, or rather, loved in spite of ourselves.";
        quotes[102] = "Happiness is a function of accepting what is.";
        quotes[103] = "Happiness is the meaning and the purpose of life, the whole aim and end of human existence.";
        quotes[104] = "Happiness is not a goal; it is a by-product.";
        quotes[105] = "Happiness is a state of activity.";
        quotes[106] = "Happiness lies in the joy of achievement and the thrill of creative effort.";
        quotes[107] = "Plenty of people miss their share of happiness, not because they never found it, but because they didn’t stop to enjoy it.";
        quotes[108] = "Happiness is an inside job.";
        quotes[109] = "If you want to be happy, be";
        quotes[110] = "If you are not happy here and now, you never will be.";
        quotes[111] = "We cannot be happy if we expect to live all the time at the highest peak of intensity. Happiness is not a matter of intensity but of balance and order and rhythm and harmony.";
        quotes[112] = "We all live with the objective of being happy; our lives are all different and yet the same.";
        quotes[113] = "I have only two kinds of days: happy and hysterically happy.";
        quotes[114] = "You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.";
        quotes[115] = "Whoever is happy will make others happy.";
        quotes[116] = "You don’t develop courage by being happy in your relationships everyday. You develop it by surviving difficult times and challenging adversity.";
        quotes[117] = "Many persons have a wrong idea of what constitutes true happiness. It is not attained through self-gratification but through fidelity to a worthy purpose.";
        quotes[118] = "Most people would rather be certain they’re miserable, than risk being happy.";
        quotes[119] = "The fact is always obvious much too late, but the most singular difference between happiness and joy is that happiness is a solid and joy a liquid.";
        quotes[120] = "Happy girls are the prettiest.";
        quotes[121] = "Folks are usually about as happy as they make their minds up to be.";
        quotes[122] = "On the whole, the happiest people seem to be those who have no particular cause for being happy except that they are so.";
        quotes[123] = "There are two ways of being happy: We must either diminish our wants or augment our means – either may do – the result is the same and it is for each man to decide for himself and to do that which happens to be easier.";
        quotes[124] = "Be happy with what you have. Be excited about what you want.";
        quotes[125] = "Success is not the key to happiness. Happiness is the key to success. If you love what you are doing, you will be successful.";
        quotes[126] = "Judge nothing, you will be happy. Forgive everything, you will be happier. Love everything, you will be happiest.";
        quotes[127] = "No one is in control of your happiness but you; therefore, you have the power to change anything about yourself or your life that you want to change.";
        quotes[128] = "Success is getting what you want, happiness is wanting what you get.";
        quotes[129] = "Be happy. It’s one way of being wise.";
        quotes[130] = "Man is fond of counting his troubles, but he does not count his joys. If he counted them up as he ought to, he would see that every lot has enough happiness provided for it.";
        quotes[131] = "Too often in life, something happens and we blame other people for us not being happy or satisfied or fulfilled. So the point is, we all have choices, and we make the choice to accept people or situations or to not accept situations.";
        quotes[132] = "Indeed, man wishes to be happy even when he so lives as to make happiness impossible.";
        quotes[133] = "The Constitution only guarantees the American people the right to pursue happiness. You have to catch it yourself.";
        quotes[134] = "The best way to cheer yourself up is to try to cheer somebody else up.";
        quotes[135] = "Can anything be so elegant as to have few wants, and to serve them one’s self?";
        quotes[136] = "The habit of being happy enables one to be freed, or largely freed, from the domination of outward conditions.";
        quotes[137] = "You can only have bliss if you don’t chase it.";
        quotes[138] = "If only we’d stop trying to be happy we could have a pretty good time.";
        quotes[139] = "Be happy for this moment. This moment is your life.";
        quotes[140] = "Happiness is like a butterfly which, when pursued, is always beyond our grasp, but, if you will sit down quietly, may alight upon you.";
        quotes[141] = "If you want to be happy, set a goal that commands your thoughts, liberates your energy, and inspires your hopes.";
        quotes[142] = "If you want others to be happy, practice compassion. If you want to be happy, practice compassion.";
        quotes[143] = "Just as a cautious businessman avoids investing all his capital in one concern, so wisdom would probably admonish us also not to anticipate all our happiness from one quarter alone.";
        quotes[144] = "A truly happy person is one who can enjoy the scenery while on a detour.";
        quotes[145] = "The only way to avoid being miserable is not to have enough leisure to wonder whether you are happy or not.";
        quotes[146] = "If there were in the world today any large number of people who desired their own happiness more than they desired the unhappiness of others, we could have a paradise in a few years.";
        quotes[147] = "Joy is a net of love by which you can catch souls.";
        quotes[148] = "When neither their property nor their honor is touched, the majority of men live content.";
        quotes[149] = "We have no more right to consume happiness without producing it than to consume wealth without producing it.";
        quotes[150] = "We are no longer happy so soon as we wish to be happier.";
        quotes[151] = "Unquestionably, it is possible to do without happiness; it is done involuntarily by nineteen-twentieths of mankind.";
        quotes[152] = "Happiness consists more in conveniences of pleasure that occur everyday than in great pieces of good fortune that happen but seldom.";
        quotes[153] = "For every minute you are angry you lose sixty seconds of happiness.";
        quotes[154] = "Happiness is when what you think, what you say, and what you do are in harmony.";
        quotes[155] = "Happiness is your dentist telling you it won’t hurt and then having him catch his hand in the drill.";
        quotes[156] = "Even a happy life cannot be without a measure of darkness, and the word happy would lose its meaning if it were not balanced by sadness. It is far better to take things as they come along with patience and equanimity.";
        quotes[157] = "It isn’t what you have or who you are or where you are or what you are doing that makes you happy or unhappy. It is what you think about it.";
        quotes[158] = "One joy scatters a hundred griefs.";
        quotes[159] = "Happiness is not something you postpone for the future; it is something you design for the present.";
        quotes[160] = "If you want happiness for an hour – take a nap. If you want happiness for a day – go fishing. If you want happiness for a month – get married. If you want happiness for a year – inherit a fortune. If you want happiness for a lifetime – help someone else.";
        quotes[161] = "The foolish man seeks happiness in the distance, the wise grows it under his feet.";
        quotes[162] = "Most of us believe in trying to make other people happy only if they can be happy in ways which we approve.";
        quotes[163] = "Learn to let go. That is the key to happiness.";

        morning_line = BitmapFactory.decodeResource(getResources(),R.drawable.m_line);
        afternoon_line = BitmapFactory.decodeResource(getResources(),R.drawable.a_line);
        evening_line = BitmapFactory.decodeResource(getResources(),R.drawable.e_line);
        night_line = BitmapFactory.decodeResource(getResources(),R.drawable.n_line);

        morning = BitmapFactory.decodeResource(getResources(),R.drawable.m_sticker);
        afternoon = BitmapFactory.decodeResource(getResources(),R.drawable.a_sticker);
        evening = BitmapFactory.decodeResource(getResources(),R.drawable.e_sticker2);
        night = BitmapFactory.decodeResource(getResources(),R.drawable.n_sticker);

        morning_first = BitmapFactory.decodeResource(getResources(),R.drawable.m_glass);
        afternoon_first = BitmapFactory.decodeResource(getResources(),R.drawable.a_glass);
        evening_first = BitmapFactory.decodeResource(getResources(),R.drawable.e_glass);
        night_first = BitmapFactory.decodeResource(getResources(),R.drawable.n_glass);

        Date_id.setText(Date);
        Time_id.setText(Time);

        fav_hr_1 = clockSettings.getInt("FavHr1", 4);
        fav_hr_2 = clockSettings.getInt("FavHr2", 5);
        fav_hr_3 = clockSettings.getInt("FavHr3", 6);
        fav_hr_4 = clockSettings.getInt("FavHr4", 7);
        fav_min_1 = clockSettings.getInt("FavMin1", 30);
        fav_min_2 = clockSettings.getInt("FavMin2", 30);
        fav_min_3 = clockSettings.getInt("FavMin3", 30);
        fav_min_4 = clockSettings.getInt("FavMin4", 30);
        if(fav_hr_1 > 9 & fav_min_1 > 9){
            sw_one.setText(fav_hr_1 + " : " + fav_min_1);
        }else if(fav_hr_1 < 10 & fav_min_1 < 10){
            sw_one.setText("0" + fav_hr_1 + " : " + "0" +fav_min_1);
        }else if(fav_hr_1 > 9 & fav_min_1 < 10){
            sw_one.setText(fav_hr_1 + " : " + "0" +fav_min_1);
        }else{
            sw_one.setText("0" + fav_hr_1 + " : " + fav_min_1);
        }

        if(fav_hr_2 > 9 & fav_min_2 > 9){
            sw_two.setText(fav_hr_2 + " : " + fav_min_2);
        }else if(fav_hr_2 < 10 & fav_min_2 < 10){
            sw_two.setText("0" + fav_hr_2 + " : " + "0" +fav_min_2);
        }else if(fav_hr_2 > 9 & fav_min_2 < 10){
            sw_two.setText(fav_hr_2 + " : " + "0" +fav_min_2);
        }else{
            sw_two.setText("0" + fav_hr_2 + " : " + fav_min_2);
        }

        if(fav_hr_3 > 9 & fav_min_3 > 9){
            sw_three.setText(fav_hr_3 + " : " + fav_min_3);
        }else if(fav_hr_3 < 10 & fav_min_3 < 10){
            sw_three.setText("0" + fav_hr_3 + " : " + "0" +fav_min_3);
        }else if(fav_hr_3 > 9 & fav_min_3 < 10){
            sw_three.setText(fav_hr_3 + " : " + "0" +fav_min_3);
        }else{
            sw_three.setText("0" + fav_hr_3 + " : " + fav_min_3);
        }

        if(fav_hr_4 > 9 & fav_min_4 > 9){
            sw_four.setText(fav_hr_4 + " : " + fav_min_4);
        }else if(fav_hr_4 < 10 & fav_min_4 < 10){
            sw_four.setText("0" + fav_hr_4 + " : " + "0" +fav_min_4);
        }else if(fav_hr_4 > 9 & fav_min_4 < 10){
            sw_four.setText(fav_hr_4 + " : " + "0" +fav_min_4);
        }else{
            sw_four.setText("0" + fav_hr_4 + " : " + fav_min_4);
        }

        enable_background(currentHourIn24Format[0], clockSettings);

        final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                assert action != null;
                if (action.equals(Intent.ACTION_TIME_TICK)) {
                    rightNow[0] = Calendar.getInstance();
                    currentHourIn24Format[0] = rightNow[0].get(Calendar.HOUR_OF_DAY);
                    currentMinute[0] = rightNow[0].get(Calendar.MINUTE);
                    currentSecond[0] = rightNow[0].get(Calendar.SECOND);
                    String Time_new;
                    if(currentHourIn24Format[0] < 10 && currentMinute[0] < 10){
                        Time_new = "0" + currentHourIn24Format[0] + " : " + "0" + currentMinute[0];
                    }
                    else if(currentHourIn24Format[0] < 10 && currentMinute[0] > 9){
                        Time_new = "0" + currentHourIn24Format[0] + " : " + currentMinute[0];
                    }else if(currentHourIn24Format[0] > 9 && currentMinute[0] < 10){
                        Time_new = currentHourIn24Format[0] + " : " + "0" + currentMinute[0];
                    }else{
                        Time_new = currentHourIn24Format[0] + " : " + currentMinute[0];
                    }
                    Time_id.setText(Time_new);

                    if(currentHourIn24Format[0] > 3 & currentHourIn24Format[0] < 12){
                        main_layout.setBackgroundColor(Color.parseColor("#f3989d"));
                        sticker.setImageBitmap(morning);
                        Message.setTextColor(Color.parseColor("#f2e3e4"));
                        divider1.setImageBitmap(morning_line);
                        divider2.setImageBitmap(morning_line);
                        divider3.setImageBitmap(morning_line);
                        Message.setText("Hi"+Name+", Good Morning\n\n"+quotes[random_no]);
                    }else if(currentHourIn24Format[0] > 11 & currentHourIn24Format[0] < 17){
                        main_layout.setBackgroundColor(Color.parseColor("#d63447"));
                        sticker.setImageBitmap(afternoon);
                        Message.setTextColor(Color.parseColor("#f9c3c3"));
                        divider1.setImageBitmap(afternoon_line);
                        divider2.setImageBitmap(afternoon_line);
                        divider3.setImageBitmap(afternoon_line);
                        Message.setText("Hi"+Name+", Good Afternoon\n\n"+quotes[random_no]);
                    }else if(currentHourIn24Format[0] > 16 & currentHourIn24Format[0] < 21){
                        main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
                        sticker.setImageBitmap(evening);
                        Message.setTextColor(Color.parseColor("#ffffff"));
                        divider1.setImageBitmap(evening_line);
                        divider2.setImageBitmap(evening_line);
                        divider3.setImageBitmap(evening_line);
                        Message.setText("Hi"+Name+", Good Evening\n\n"+quotes[random_no]);
                    }else {
                        main_layout.setBackgroundColor(Color.parseColor("#202020"));
                        sticker.setImageBitmap(night);
                        Message.setTextColor(Color.parseColor("#807e7e"));
                        divider1.setImageBitmap(night_line);
                        divider2.setImageBitmap(night_line);
                        divider3.setImageBitmap(night_line);
                        Message.setText("Hi"+Name+", Good Night\n\n"+quotes[random_no]);
                    }
                }
            }
        };

        sw_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String str_time = (String) sw_one.getText();
                String str_hour=str_time.substring(0,2);
                String str_minute=str_time.substring(5,7);
                int int_hour=Integer.parseInt(str_hour);
                int int_minute=Integer.parseInt(str_minute);
                int notID1 = (int_hour * 100) + int_minute;
                if(isChecked){
                    setAlarm(notID1, int_hour, int_minute, clockSettings);
                }else{
                    resetAlarm(notID1);
                }
            }
        });

        edit_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupActivity dialog = new popupActivity();
                dialog.setTargetFragment(Clock.this, 1);
                dialog.show(getFragmentManager(), "popupActivity");
            }
        });

        getActivity().registerReceiver(m_timeChangedReceiver, s_intentFilter);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            displayMessage(getContext(),"Youtube Alarm will not work without this permission");
        }
        return;
    }

    private void requestPermissions(){
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);
            return;
        }

        if (getContext().checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_INTERNET);
            return;
        }

        if (getContext().checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_USE_FULL_SCREEN_INTENT);
            return;
        }

        if (getContext().checkSelfPermission(Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_WAKE_LOCK);
            return;
        }
    }

    String find_day(String number){
        String output;
        switch (number){
            case "1": output = "Sunday";
                break;
            case "2": output = "Monday";
                break;
            case "3": output = "Tuesday";
                break;
            case "4": output = "Wednesday";
                break;
            case "5": output = "Thursday";
                break;
            case "6": output = "Friday";
                break;
            case "7": output = "Saturday";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
        return output;
    }

    String find_month(String number){
        String output;
        switch (number){
            case "0": output = "01";
                break;
            case "1": output = "02";
                break;
            case "2": output = "03";
                break;
            case "3": output = "04";
                break;
            case "4": output = "05";
                break;
            case "5": output = "06";
                break;
            case "6": output = "07";
                break;
            case "7": output = "08";
                break;
            case "8": output = "09";
                break;
            case "9": output = "10";
                break;
            case "10": output = "11";
                break;
            case "11": output = "12";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
        return output;
    }

    String find_date(String number){
        String output;
        switch (number){
            case "1": output = "01";
                break;
            case "2": output = "02";
                break;
            case "3": output = "03";
                break;
            case "4": output = "04";
                break;
            case "5": output = "05";
                break;
            case "6": output = "06";
                break;
            case "7": output = "07";
                break;
            case "8": output = "08";
                break;
            case "9": output = "09";
                break;
            default: output = number;
        }
        return output;
    }

    @SuppressLint("SetTextI18n")
    void enable_background(int currentHourIn24Format, SharedPreferences clockSettings){
        Name = clockSettings.getString("UserName", "");

        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            main_layout.setBackgroundColor(Color.parseColor("#f3989d"));
            sticker.setImageBitmap(morning);
            Message.setTextColor(Color.parseColor("#f2e3e4"));
            divider1.setImageBitmap(morning_line);
            divider2.setImageBitmap(morning_line);
            divider3.setImageBitmap(morning_line);
            Message.setText("Hi"+Name+", Good Morning\n\n"+quotes[random_no]);
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout.setBackgroundColor(Color.parseColor("#d63447"));
            sticker.setImageBitmap(afternoon);
            Message.setTextColor(Color.parseColor("#f9c3c3"));
            divider1.setImageBitmap(afternoon_line);
            divider2.setImageBitmap(afternoon_line);
            divider3.setImageBitmap(afternoon_line);
            Message.setText("Hi"+Name+", Good Afternoon\n\n"+quotes[random_no]);
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
            sticker.setImageBitmap(evening);
            Message.setTextColor(Color.parseColor("#ffffff"));
            divider1.setImageBitmap(evening_line);
            divider2.setImageBitmap(evening_line);
            divider3.setImageBitmap(evening_line);
            Message.setText("Hi"+Name+", Good Evening\n\n"+quotes[random_no]);
        }else {
            main_layout.setBackgroundColor(Color.parseColor("#202020"));
            sticker.setImageBitmap(night);
            Message.setTextColor(Color.parseColor("#807e7e"));
            divider1.setImageBitmap(night_line);
            divider2.setImageBitmap(night_line);
            divider3.setImageBitmap(night_line);
            Message.setText("Hi"+Name+", Good Night\n\n"+quotes[random_no]);
        }
    }

    void setAlarm(int notificationId, int hour, int minute, SharedPreferences clockSettings){
        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        SharedPreferences.Editor prefEditor = clockSettings.edit();
        prefEditor.putString("Keyword", kw[0]);
        prefEditor.apply();

        displayMessage(globalContext, "Alarm has been Set");

        Intent intent = new Intent(getContext(), AlarmBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0);


        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        Calendar c = Calendar.getInstance();

        long timeAtButtonClick = System.currentTimeMillis();
        long final_hour = hour - currentHourIn24Format[0];
        long final_minute = minute - currentMinute[0];
        long final_second = minute - currentSecond[0];
        long total_min_alarm = (final_hour * 60) + final_minute;
        long total_sec_alarm = (total_min_alarm * 60) + final_second;
        long total_msec_alarm = total_sec_alarm * 1000;
        if(total_msec_alarm < 0){
            total_msec_alarm += 86400000;
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + total_msec_alarm, pendingIntent);
    }

    void resetAlarm(int notificationId){
        Intent intent_stop = new Intent(getContext(), AlarmBroadcast.class);
        AlarmManager alarmManager_stop = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(globalContext, notificationId, intent_stop, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        alarmManager_stop.cancel(pendingIntent);
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void sendInput(String input) {
        sw_one.setText(input);
    }
}