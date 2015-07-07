package com.trend.tobeylin.tobeytrend;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.android.volley.Response;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by tobeylin on 15/7/6.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestKeywordGenerator extends InstrumentationTestCase {

    VolleyRequestQueue volleyRequestQueue;
    KeywordGenerator keywordGenerator;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Add when testing is running on the virtual machine.
        //System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

        Context context = mock(Context.class);
        volleyRequestQueue = mock(VolleyRequestQueue.class);
        keywordGenerator = KeywordGenerator.getInstance(context);
        keywordGenerator.setVolleyRequestQueue(volleyRequestQueue);
        keywordGenerator.sync();
    }

    @Test
    public void testSyncSuccess() {
        ArgumentCaptor<Response.Listener> successCaptor = ArgumentCaptor.forClass(Response.Listener.class);
        verify(volleyRequestQueue).sendGetRequest(anyString(), successCaptor.capture(), any(Response.ErrorListener.class));
        String testApiResponse = "{\"42\": [\"Grekland\", \"Nina Hemmingsson\", \"Scott Disick\", \"Lotta P\\u00e5 Liseberg 2015\", \"Imad Khalili\", \"Kajsa Ekis Ekman\", \"Fabian Bolin\", \"Copa America\", \"Liza Marklund\", \"Allsvenskan\", \"Noel Gallagher\", \"Anna Kinberg Batra\", \"\\u00c5sa Romson\", \"Putte i Parken\", \"Sandra Nilsson\", \"Ebba von Sydow\", \"Riksbanken\", \"Elton John Uppsala\", \"Ludwig Augustinsson\", \"Sverige\"], \"43\": [\"Lesbos\", \"Jan Hus\", \"Akcie\", \"Ceska Televize\", \"jeseter\", \"Predpoved Pocasi\", \"Cyril a Metod\\u011bj\", \"Richard Gere\", \"Tour de France\", \"Alb\\u00e1nie\", \"Ivan Pal\\u00fach\", \"MERS\", \"Krist\\u00fdna Pl\\u00ed\\u0161kov\\u00e1\", \"Richard Gere\", \"Alessandra Ambrosio\", \"Jennie Garth\", \"Nicholas Winton\", \"Airbus A380\", \"Apple Music\", \"Nicole Scherzinger\"], \"49\": [\"Eiji Tsuburaya\", \"Star Tour\", \"Wimbledon\", \"Caroline Wozniacki\", \"Chloe Bartoli\", \"Elton John\", \"Greece\", \"Arda Turan\", \"Fifty Shades of Grey\", \"Josie Cunningham\", \"Terminator\", \"Carli Lloyd\", \"Mila Kunis\", \"DMI\", \"Komodo\", \"Mangan\", \"Argon\", \"Emma Hamilton\", \"Radon\", \"GI Joe\"], \"52\": [\"Jos Bomb Blast\", \"Arda Turan\", \"Women S World Cup 2015\", \"Copa America final\", \"NYSC\", \"Argentina vs Paraguay\", \"Arda Turan\", \"BVN\", \"BET AWARDS 2015\", \"Helipad\", \"Arturo Vidal\", \"Argentina vs Colombia\", \"Michael Jackson\", \"Roberto Firmino\", \"PING\", \"Ariwera Jonathan\", \"Petr Cech\", \"What Is Production\", \"Fathers Day Messages\", \"Carole King\"], \"24\": [\"Eiji Tsuburaya\", \"TEOG tercih\", \"Arda Turan\", \"Son depremler\", \"Nani\", \"Ehliyet S\\u0131nav Sonu\\u00e7lar\\u0131\", \"Tatl\\u0131 K\\u00fc\\u00e7\\u00fck Yalanc\\u0131lar\", \"gergedan b\\u00f6ce\\u011fi\", \"Kevin Prince Boateng\", \"Bensu Soral\", \"Kadir gecesi\", \"Emre Kula\", \"Manisa haber\", \"E Devlet Giri\\u015f\", \"Tehlikeli Madde G\\u00fcvenli\\u011fi Dan\\u0131\\u015fmanl\\u0131\\u011f\\u0131\", \"\\u00d6SYM Aday \\u0130\\u015flemleri\", \"Kpss Sonu\\u00e7lar\\u0131\", \"Musakka\", \"Ramazan Bayrami 2015\", \"Aziz Nesin\"], \"25\": [\"Eiji Tsuburaya\", \"Magic Mike XXL\", \"Chloe Bartoli\", \"GMA News\", \"MERS CoV\", \"Class Suspension Today\", \"Lou Williams\", \"Class Suspension Today In Manila\", \"Mist\", \"The Scientist\", \"Lyme disease\", \"UST fire\", \"Coleen Garcia\", \"suspension of classes\", \"Rappler\", \"Eid Al Fitr 2015\", \"Mila Kunis\", \"Yanet Garcia\", \"Class Suspension July 6 2015\", \"Pagasa\"], \"26\": [\"Arda Turan\", \"Mujeres y Hombres y Viceversa\", \"San Fermin\", \"Casillas\", \"Alex Morgan\", \"Juli Soler\", \"Hope Solo\", \"Mila Kunis\", \"Bakkali\", \"GoPro\", \"Daniella Chavez\", \"Alexis Tsipras\", \"Grecia\", \"Varoufakis\", \"Referendum Grecia 2015\", \"Kenny Noyes\", \"Oxi\", \"Dragon Ball Super\", \"Messi\", \"Copa America Final\"], \"27\": [\"Eiji Tsuburaya\", \"Ansa\", \"SOMETHING BORROWED\", \"Oxi\", \"Pirlo\", \"Terminator\", \"giornata mondiale del bacio\", \"JOSE MAURI\", \"Grexit\", \"Borsa Milano\", \"GoPro\", \"Arda Turan\", \"Calciatori Brutti\", \"Marchisio\", \"Frida Kahlo\", \"Hacking Team\", \"Proverbi\", \"Grecia Fuori Dall Euro\", \"Djokovic\", \"Alexis Tsipras\"], \"21\": [\"Frida Kahlo\", \"UAQ\", \"Kourtney Kardashian\", \"Ronaldo Gay\", \"Arda Turan\", \"Mila Kunis\", \"Demi Lovato\", \"Dolar Hoy\", \"Paulina Vega\", \"Abby Wambach\", \"Elizabeth Olsen\", \"Alibaba\", \"Acueducto del Padre Tembleque\", \"Alex Morgan\", \"Grecia\", \"Ronaldo\", \"Hope Solo\", \"El Tiempo\", \"Master Chef Mexico\", \"Copa Mundial Femenina 2015\"], \"48\": [\"\\u03a0\\u03b5\\u03b9\\u03c1\\u03b1\\u03b9\\u03c9\\u03c3\", \"\\u03a4\\u03c3\\u03b1\\u03ba\\u03b1\\u03bb\\u03c9\\u03c4\\u03bf\\u03c3\", \"\\u0392\\u03b1\\u03c1\\u03bf\\u03c5\\u03c6\\u03b1\\u03ba\\u03b7\\u03c3\", \"\\u03a3\\u03b1\\u03bc\\u03b1\\u03c1\\u03b1\\u03c3\", \"Financial Times\", \"CNN\", \"\\u03a7\\u03bf\\u03c5\\u03bb\\u03b9\\u03b1\\u03c1\\u03b1\\u03ba\\u03b7\\u03c3\", \"Varoufakis\", \"ELA\", \"IOU\", \"\\u0391\\u03bb\\u03ad\\u03be\\u03b7\\u03c2 \\u03a4\\u03c3\\u03af\\u03c0\\u03c1\\u03b1\\u03c2\", \"Spiegel\", \"\\u0396\\u03c9\\u03ae \\u039a\\u03c9\\u03bd\\u03c3\\u03c4\\u03b1\\u03bd\\u03c4\\u03bf\\u03c0\\u03bf\\u03cd\\u03bb\\u03bf\\u03c5\", \"\\u03a7\\u03c1\\u03b7\\u03bc\\u03b1\\u03c4\\u03b9\\u03c3\\u03c4\\u03b7\\u03c1\\u03b9\\u03b1\", \"\\u03a0\\u03bf\\u03c4\\u03b5 \\u0391\\u03bd\\u03bf\\u03b9\\u03b3\\u03bf\\u03c5\\u03bd \\u039f\\u03b9 \\u03a4\\u03c1\\u03b1\\u03c0\\u03b5\\u03b6\\u03b5\\u03c3\", \"Bild\", \"Liberation\", \"\\u039c\\u03b5\\u03c1\\u03ba\\u03b5\\u03bb\", \"\\u03a0\\u03b5\\u03c1\\u03b9\\u03c3\\u03c4\\u03b5\\u03c1\\u03b1 \\u039c\\u03c0\\u03b1\\u03b6\\u03b9\\u03b1\\u03bd\\u03b1\", \"Le Monde\"], \"23\": [\"\\uba54\\uc774\\ud50c\\uc2a4\\ud1a0\\ub9ac2\", \"\\uac78\\uc2a4\\ub370\\uc774\", \"\\ub77c\\uc724\\uacbd\", \"\\uae40\\uc740\\uc624\", \"\\uadf8\\ub9ac\\uc2a4\", \"\\uc5d8\\ub9ac\\uc790\\ubca0\\uc2a4 \\uc62c\\uc2a8\", \"\\uae40\\uc5f0\\uc6b0\", \"\\uc784\\ud0dc\\ud6c8\", \"\\uc2ec\\uc57c\\uc2dd\\ub2f9\", \"\\uae40\\uc0c8\\ub86c\", \"\\ub85c\\ub610657\", \"\\ud601\\uc624\", \"\\uc624\\ub098\\uc758\\uadc0\\uc2e0\\ub2d8\", \"\\uc9c0\\uc5f0\", \"\\ud55c\\uacbd\\uc120\", \"\\uc1fc\\ubbf8\\ub354\\uba38\\ub2c84\", \"\\uae40\\ud558\\ub298\", \"\\ud0dc\\ud48d\", \"\\ud55c\\ud654\\ucf00\\ubbf8\\uce7c\", \"\\ub124\\ub124\\uce58\\ud0a8\"], \"46\": [\"Eiji Tsuburaya\", \"Wimbledon\", \"Wetter Schweiz\", \"Ventilator\", \"Milo Moir\\u00e9\", \"Varoufakis\", \"Djokovic\", \"Federer\", \"Rts Sport\", \"Yanis Varoufakis\", \"Wawrinka\", \"Cancellara\", \"Roger Federer\", \"GoPro\", \"Valdet Gashi\", \"Arda Turan\", \"Mila Kunis\", \"Tennis\", \"Tatort\", \"Kourtney Kardashian\"], \"47\": [\"Maxi Pereira\", \"Damon Albarn\", \"Casillas\", \"marca\", \"As\", \"Kourtney Kardashian\", \"GoPro\", \"Hope Solo\", \"Tour\", \"Mila Kunis\", \"Euclid Tsakalotos\", \"Rui Costa\", \"Arda Turan\", \"Jorge Jesus\", \"Maicon\", \"Tsipras\", \"Syriza\", \"Nani\", \"Alexis Tsipras\", \"Paulo Neves\"], \"44\": [\"Eiji Tsuburaya\", \"Milo Moir\\u00e9\", \"Tag des Kusses\", \"Ben Tewaag\", \"Arda Turan\", \"Yanis Varoufakis\", \"GoPro\", \"Pietro Lombardi Baby\", \"Mila Kunis\", \"Ziemlich beste Freunde\", \"Bachelorette 2015\", \"Jane the Virgin\", \"Mama\", \"WetterOnline\", \"Mikronesien\", \"Frauen Fu\\u00dfball-weltmeisterschaft Finale 2015\", \"Griechenland\", \"Griechenland Referendum\", \"Abstimmung Griechenland\", \"Copa America\"], \"45\": [\"Morning Show\", \"Mila Kunis\", \"Arda Turan\", \"Bety\\u00e1rsereg\", \"Beck Judit\", \"Tokhal\", \"Copa America\", \"G\\u00f6r\\u00f6g N\\u00e9pszavaz\\u00e1s\", \"fel\", \"Silverstone\", \"John Newman\", \"Tour de France\", \"M7 Aut\\u00f3p\\u00e1lya\", \"j\\u00falius 4\", \"Redbull Air Race\", \"F\\u00f6ldes Eszter\", \"Szabad Gy\\u00f6rgy\", \"K\\u00e1llai Istv\\u00e1n\", \"Red Bull Air Race\", \"Overdose\"], \"28\": [\"H\\u00e0o Anh\", \"Ninh D\\u01b0\\u01a1ng Lan Ng\\u1ecdc\", \"Sinh Nhat Son Tung\", \"M\\u01b0a m\\u00e1u\", \"Chile vs Argentina\", \"Guong Mat Than Quen Tap 12\", \"Bi Mat Dem Chu Nhat Tap 2\", \"Lee Dong Gun\", \"Nhac Si An Thuyen\", \"Phung Quang Thanh\", \"\\u0110\\u00e1p \\u00c1n L\\u00fd 2015\", \"Dap An De Thi Van 2015\", \"Trong Nghia Keo Keo\", \"Thach Thuc Danh Hai Tap 12\", \"De Thi Mon Van\", \"Trung Qu\\u1ed1c\", \"\\u0110\\u00e1p \\u00c1n M\\u00f4n V\\u0103n 2015\", \"De Thi Mon Toan\", \"\\u00c1nh Vi\\u00ean\", \"L\\u1ecbch Thi Thpt Qu\\u1ed1c Gia 2015\"], \"29\": [\"\\u0627\\u064a\\u062c\\u064a \\u062a\\u0633\\u0648\\u0628\\u0648\\u0631\\u0627\\u064a\\u0627\", \"\\u0643\\u0648\\u0631\\u0629 \\u0627\\u0648\\u0646 \\u0644\\u0627\\u064a\\u0646\", \"\\u0645\\u0628\\u0627\\u0631\\u0627\\u0629 \\u0627\\u0644\\u0627\\u0647\\u0644\\u0649 \\u0627\\u0644\\u064a\\u0648\\u0645\", \"\\u0627\\u062d\\u0645\\u062f \\u0627\\u0644\\u062a\\u0628\\u0627\\u0639\", \"\\u0645\\u0628\\u0627\\u0631\\u0627\\u0629 \\u0627\\u0644\\u0632\\u0645\\u0627\\u0644\\u0643 \\u0627\\u0644\\u064a\\u0648\\u0645\", \"\\u0645\\u0633\\u0644\\u0633\\u0644 \\u0644\\u0639\\u0628\\u0629 \\u0627\\u0628\\u0644\\u064a\\u0633 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 19\", \"\\u0645\\u0633\\u0644\\u0633\\u0644 \\u062d\\u0627\\u0644\\u0629 \\u0639\\u0634\\u0642 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 19\", \"\\u0645\\u0633\\u0644\\u0633\\u0644 \\u0648\\u0644\\u0649 \\u0627\\u0644\\u0639\\u0647\\u062f \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 18\", \"\\u0627\\u0644\\u0643\\u0628\\u064a\\u0631 \\u0627\\u0648\\u0649 \\u0627\\u0644\\u062c\\u0632\\u0621 \\u0627\\u0644\\u062e\\u0627\\u0645\\u0633 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 19\", \"\\u0645\\u0633\\u0644\\u0633\\u0644 \\u0644\\u0647\\u0641\\u0629 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 18\", \"\\u0631\\u0627\\u0645\\u0632 \\u0648\\u0627\\u0643\\u0644 \\u0627\\u0644\\u062c\\u0648 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 18\", \"\\u062a\\u0631\\u062f\\u062f \\u0642\\u0646\\u0627\\u0629 CRT\", \"\\u0627\\u0644\\u064a\\u0648\\u0646\\u0627\\u0646\", \"\\u0631\\u064a\\u0645 \\u0627\\u0644\\u0628\\u0627\\u0631\\u0648\\u062f\\u064a\", \"\\u0644\\u0639\\u0628\\u0629 \\u0627\\u0628\\u0644\\u064a\\u0633 18\", \"\\u0646\\u0641\\u0631\\u062a\\u064a\\u062a\\u064a\", \"\\u0633\\u0639\\u0631 \\u0627\\u0644\\u062f\\u0648\\u0644\\u0627\\u0631 \\u0627\\u0644\\u064a\\u0648\\u0645\", \"\\u0631\\u0627\\u0645\\u0632 \\u0648\\u0627\\u0643\\u0644 \\u0627\\u0644\\u062c\\u0648 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 17\", \"\\u0645\\u0633\\u0644\\u0633\\u0644 \\u0645\\u0648\\u0644\\u0627\\u0646\\u0627 \\u0627\\u0644\\u0639\\u0627\\u0634\\u0642 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 17\", \"\\u0627\\u0633\\u0639\\u0627\\u0631 \\u0627\\u0644\\u0639\\u0645\\u0644\\u0627\\u062a\"], \"40\": [\"Eiji Tsuburaya\", \"Wimbledon\", \"Kevin Anderson\", \"Kourtney Kardashian\", \"Alex Morgan\", \"Women S World Cup 2015\", \"David Masondo\", \"Greece\", \"Princess Charlotte\", \"Copa Am\\u00e9rica\", \"Tour De France\", \"Bafana Bafana\", \"4th Of July\", \"Vodacom Durban July\", \"Durban July 2015\", \"Heather Watson\", \"Dustin Brown\", \"Durban July\", \"Jennifer Garner\", \"Frans Matlala\"], \"41\": [\"Eiji Tsuburaya\", \"le Tour de France\", \"Goffin\", \"Varoufakis\", \"Wimbledon Live\", \"Greece\", \"Kourtney Kardashian\", \"Tour Antwerpen\", \"Ronde Van Frankrijk 2015 Antwerpen\", \"Arda Turan\", \"Dessel\", \"Tour De France Live\", \"Huy\", \"Renault Talisman\", \"Eddy Merckx\", \"Milo Moir\\u00e9\", \"Kinkhoest\", \"Hope Solo\", \"Letour\", \"Referendum Grece\"], \"1\": [\"Chloe Bartoli\", \"Bill Cosby\", \"Serena Williams\", \"Amanda Peterson\", \"Mila Kunis\", \"ESPN Body Issue\", \"David West\", \"GoPro\", \"Deandre Johnson\", \"Elizabeth Olsen\", \"The Gallows\", \"Margot Robbie\", \"Burt Shavitz\", \"Amazon Prime Day\", \"Rory McIlroy\", \"Fox\", \"Walter Reed\", \"Gold Cup\", \"Demi Lovato\", \"Hope Solo\"], \"3\": [\"Eiji Tsuburaya\", \"Federer\", \"Roger Federer\", \"Wimbledon 2015\", \"SpiceJet\", \"Housing\", \"Welcome Back\", \"What Is Vyapam Scam\", \"BTEUP\", \"Greece\", \"Mira Rajput\", \"Deepika Padukone\", \"S Cross\", \"Women S World Cup 2015\", \"Copa America final\", \"UPSC\", \"Vyapam scam\", \"Independence Day USA\", \"Heather Watson\", \"continue\"], \"5\": [\"Eiji Tsuburaya\", \"Hope Solo\", \"Varoufakis\", \"ringgit\", \"Amos Yee\", \"Greece\", \"Women S World Cup\", \"wombat\", \"Vyapam Scam\", \"Lyme disease\", \"Dragon Ball Super\", \"Mila Kunis\", \"Women S World Cup 2015\", \"Greece News\", \"Princess Charlotte\", \"Copa America final\", \"UNESCO\", \"Sundown Marathon\", \"4th of July\", \"NDP 2015\"], \"4\": [\"\\u5186\\u8c37\\u82f1\\u4e8c\", \"\\u8d64\\u9aea\\u306e\\u767d\\u96ea\\u59eb\", \"\\u30b9\\u30ab\\u30c3\\u3068\\u30b8\\u30e3\\u30d1\\u30f3\", \"\\u306a\\u3067\\u3057\\u3053\\u30b8\\u30e3\\u30d1\\u30f3\", \"\\u306a\\u3067\\u3057\\u3053\", \"\\u306a\\u3067\\u3057\\u3053\\u901f\\u5831\", \"\\u30b5\\u30c3\\u30ab\\u30fc\", \"\\u4e03\\u5915\", \"\\u30e2\\u30f3\\u30b9\\u30c8 \\u30de\\u30ea\\u30fc\\u30c0\", \"\\u5c71\\u5d0e\\u8ce2\\u4eba\", \"\\u677e\\u5c71\\u30b1\\u30f3\\u30a4\\u30c1\", \"\\u5973\\u5b50\\u30b5\\u30c3\\u30ab\\u30fc\", \"\\u5730\\u9707\", \"\\u30ef\\u30fc\\u30eb\\u30c9\\u30ab\\u30c3\\u30d7\", \"\\u4e2d\\u6751\\u30a2\\u30f3\", \"\\u6fa4\\u7a42\\u5e0c\", \"\\u5973\\u5b50\\u30b5\\u30c3\\u30ab\\u30fc \\u30ef\\u30fc\\u30eb\\u30c9\\u30ab\\u30c3\\u30d7\", \"\\u6c34\\u6ca2\\u30a2\\u30ad\", \"\\u30ef\\u30f3\\u30d0\\u30c3\\u30af\", \"\\u8ecd\\u8266\\u5cf6\"], \"6\": [\"Eiji Tsuburaya\", \"GoPro\", \"Popcorn Time\", \"Greece\", \"\\u05d1\\u05d9\\u05ea \\u05e9\\u05e2\\u05e8\\u05d9\\u05dd\", \"\\u05d2 \\u05d5\\u05e8\\u05d3\\u05df \\u05e4\\u05d0\\u05e8\\u05de\\u05e8\", \"\\u05d0\\u05e4\\u05e8\\u05d9\\u05dd \\u05d1\\u05e8\\u05db\\u05d4\", \"\\u05d9\\u05d5\\u05d0\\u05d1 \\u05d9\\u05e6\\u05d7\\u05e7\", \"\\u05e4\\u05d5\\u05e4\\u05e7\\u05d5\\u05e8\\u05df \\u05d8\\u05d9\\u05d9\\u05dd\", \"\\u05d4\\u05e8\\u05d1 \\u05e4\\u05d9\\u05e0\\u05d8\\u05d5\", \"Copa America\", \"\\u05d4\\u05e8\\u05d1 \\u05de\\u05d4\\u05e6\\u05e4\\u05d5\\u05df\", \"\\u05ea\\u05ea \\u05e0\\u05d9\\u05e6\\u05d1 \\u05d1\\u05e8\\u05db\\u05d4\", \"ISIS\", \"\\u05de\\u05e1\\u05d9\", \"\\u05d4\\u05e8\\u05d1 \\u05de\\u05e6\\u05e4\\u05ea\", \"\\u05e6\\u05d5\\u05dd \\u05d9\\u05d6 \\u05d1\\u05ea\\u05de\\u05d5\\u05d6\", \"\\u05e1\\u05d9\\u05e8\\u05e7 \\u05d3\\u05d4 \\u05e1\\u05d5\\u05dc\\u05d9\\u05d9\", \"\\u05d2\\u05de\\u05e8 \\u05e7\\u05d5\\u05e4\\u05d4 \\u05d0\\u05de\\u05e8\\u05d9\\u05e7\\u05d4 2015\", \"\\u05d4\\u05de\\u05d9\\u05e0\\u05d9\\u05d5\\u05e0\\u05d9\\u05dd\"], \"9\": [\"Eiji Tsuburaya\", \"Andy Murray\", \"Kevin Anderson\", \"Kourtney Kardashian\", \"Serena Williams\", \"Tube Strike\", \"Simon Cowell\", \"Nick Kyrgios\", \"Charles Bronson\", \"Roger Federer\", \"Hope Solo\", \"Brighton bus crash\", \"Bbc Sport Tennis\", \"Margot Robbie\", \"Alex Morgan\", \"Arda Turan\", \"Rory McIlroy\", \"The Grey\", \"Sean O Driscoll\", \"Tyson Fury\"], \"8\": [\"Eiji Tsuburaya\", \"Kourtney Kardashian\", \"Wimbledon results\", \"GoPro\", \"Andy Murray\", \"Charles Bronson\", \"Joe Manganiello\", \"Parramatta Eels\", \"Nick Kyrgios\", \"Emily Blunt\", \"Mila Kunis\", \"austerity\", \"NAIDOC\", \"Paper Towns\", \"Australian dollar\", \"Yanis Varoufakis\", \"Scott Camporeale\", \"Hope Solo\", \"James Hird\", \"Gina Rinehart\"], \"51\": [\"Arda Turan\", \"\\u00d8rjan Bur\\u00f8e\", \"Scott Disick\", \"GoPro\", \"Hope Solo\", \"Ironman Haugesund 2015\", \"Hornindal Rundt\", \"Ironman\", \"4 Juli\", \"Kygo Take On Me\", \"Tour de France\", \"Ironman Haugesund\", \"Astrid Smeplass\", \"Dronning Sonja\", \"Reddit\", \"Elton John\", \"Marc Jacobs\", \"Lofotposten\", \"Magic Mike XXL\", \"Smaalenene\"], \"39\": [\"EIJI TSUBURAYA\", \"Monica Niculescu\", \"Darius Dadoo\", \"Yanis Varoufakis\", \"INSULA IUBIRII 5 IULIE\", \"Referendum Grecia 2015\", \"Ziua Internationala a Sarutului\", \"Hamroun\", \"Alexis Tsipras\", \"Repartizare Licee 2015\", \"ASA TG Mures\", \"Rezultate BAC 2015\", \"Edu.ro Rezultate Bac 2015\", \"REFERENDUM GRECIA\", \"Grecia\", \"CHILE ARGENTINA\", \"Vatafu TV\", \"Rezultate Referendum Grecia\", \"Michael Douglas\", \"Valeriu Zgonea\"], \"38\": [\"Federico Langone\", \"LAN\", \"Banco de Chile\", \"Registro Civil\", \"Metro de Santiago\", \"Sky\", \"Frida Kahlo\", \"WOM\", \"Mila Kunis\", \"Alex Morgan\", \"banco\", \"Banco Central\", \"Daniella Chavez\", \"Dragon Ball Super\", \"El Tiempo\", \"El Clarin Argentina\", \"memes\", \"Sitiados\", \"El Grafico\", \"Higuain\"], \"10\": [\"\\u5f35\\u6587\\u91c7\", \"\\u6e2f\\u4ea4\\u6240\", \"\\u8d99\\u96c5\\u829d\", \"\\u99ac\\u5929\\u5b87\", \"\\u4e2d\\u4e00\\u6d3e\\u4f4d\", \"\\u6b7b\\u4ea1\\u7b46\\u8a18\", \"\\u963f\\u821c\", \"A\\u80a1\", \"\\u4e0a\\u8b49\\u6307\\u6578\", \"\\u4f59\\u6f8e\\u6749\", \"\\u571f\\u8033\\u5176\", \"\\u5e0c\\u81d8\", \"Uber\", \"\\u99ac\\u6703\", \"\\u674e\\u78a7\\u83ef\", \"\\u5f35\\u975a\\u7a4e\", \"\\u674e\\u6771\\u5065\", \"Deep Web\", \"\\u5f35\\u6f64\\u8861\", \"\\u516b\\u4ed9\\u5dba\\u5c71\\u706b\"], \"13\": [\"Kourtney Kardashian\", \"NASCAR\", \"Pemberton Fire\", \"Mila Kunis\", \"Bill Cosby\", \"Sarah Huffman\", \"Pan Am Tickets\", \"Amanda Peterson\", \"Shawn Matthias\", \"Greece\", \"Bc Forest Fires\", \"Coupe du Monde f\\u00e9minine 2015\", \"Hope Solo\", \"Princess Charlotte\", \"Burnaby Mountain Fire\", \"Cory Joseph\", \"Vancouver fire\", \"Alex Morgan\", \"BC wildfire\", \"smoke in Vancouver\"], \"12\": [\"\\u738b\\u54c1\", \"\\u674e\\u6c9b\\u65ed\", \"\\u9673\\u5929\\u9806\", \"\\u4e0a\\u539f\\u4e9e\\u8863\", \"\\u9802\\u57d4\\u7ad9\", \"\\u8f9c\\u839e\\u5141\", \"\\u6b7b\\u4ea1\\u7b46\\u8a18\\u672c\", \"\\u884c\\u653f\\u9662\\u4eba\\u4e8b\\u884c\\u653f\\u7e3d\\u8655\", \"\\u9f0e\\u6cf0\\u8c50\", \"\\u4e2d\\u592e\", \"\\u674e\\u6176\\u5143\", \"\\u4e2d\\u592e\\u6c23\\u8c61\\u5c40\", \"\\u5e0c\\u81d8\\u516c\\u6295\", \"\\u5f35\\u975a\\u7a4e\", \"\\u8cc8\\u6c38\\u5a55\", \"\\u9673\\u6f84\\u6ce2\", \"\\u5ed6\\u6dfb\\u4e01\", \"E-bike\", \"\\u9ed1\\u5929\\u9d5d\", \"\\u9673\\u67cf\\u5ef7\"], \"15\": [\"Eiji Tsuburaya\", \"Griechenland\", \"Bachelorette 2015\", \"Tag des Kusses\", \"Hope Solo\", \"Yanis Varoufakis\", \"Udo Lindenberg\", \"taz\", \"Griechenland Referendum\", \"Ironman Frankfurt\", \"Unwetterwarnung\", \"Abstimmung Griechenland\", \"Copa America\", \"Sherlock Holmes\", \"Taufe Charlotte\", \"Gewitter\", \"Sidney Sam\", \"Oxi\", \"Jan Frodeno\", \"Frauenfu\\u00dfball\"], \"14\": [\"\\u0427\\u0435\\u0440\\u043d\\u043e\\u0431\\u044b\\u043b\\u044c \\u0417\\u043e\\u043d\\u0430 \\u041e\\u0442\\u0447\\u0443\\u0436\\u0434\\u0435\\u043d\\u0438\\u044f\", \"\\u041c\\u0430\\u043a\\u0430\\u0440\\u0435\\u0432\\u0438\\u0447\", \"\\u0414\\u0435\\u043d\\u044c \\u043f\\u043e\\u0446\\u0435\\u043b\\u0443\\u0435\\u0432\", \"\\u0418\\u0432\\u0430\\u043d \\u041a\\u0443\\u043f\\u0430\\u043b\\u0430\", \"\\u043c\\u043e\\u043d\\u043e\\u043d\\u0443\\u043a\\u043b\\u0435\\u043e\\u0437\", \"\\u0413\\u0435\\u0440\\u043c\\u0430\\u043d \\u0421\\u0442\\u0435\\u0440\\u043b\\u0438\\u0433\\u043e\\u0432\", \"\\u0413\\u0440\\u0435\\u0446\\u0438\\u044f\", \"\\u0427\\u0438\\u043b\\u0438 \\u0410\\u0440\\u0433\\u0435\\u043d\\u0442\\u0438\\u043d\\u0430\", \"\\u0417\\u0435\\u043c\\u0444\\u0438\\u0440\\u0430\", \"\\u041a\\u0443\\u0440\\u0431\\u0430\\u043d \\u041e\\u043c\\u0430\\u0440\\u043e\\u0432\", \"\\u0414\\u0435\\u043d\\u044c \\u0413\\u0410\\u0418\", \"\\u0421\\u043e\\u0441\\u043e \\u041f\\u0430\\u0432\\u043b\\u0438\\u0430\\u0448\\u0432\\u0438\\u043b\\u0438\", \"\\u0422\\u0435\\u0440\\u043c\\u0438\\u043d\\u0430\\u0442\\u043e\\u0440 \\u0413\\u0435\\u043d\\u0435\\u0437\\u0438\\u0441\", \"\\u0410\\u0440\\u0433\\u0435\\u043d\\u0442\\u0438\\u043d\\u0430 \\u041f\\u0430\\u0440\\u0430\\u0433\\u0432\\u0430\\u0439\", \"Apple Music\", \"Meizu MX5\", \"\\u0421\\u043e\\u0431\\u043b\\u0430\\u0437\\u043d\", \"\\u041f\\u0440\\u0438\\u043c\\u0430\\u043a\\u043e\\u0432\", \"Falcon 9\", \"\\u0410\\u0440\\u0433\\u0435\\u043d\\u0442\\u0438\\u043d\\u0430 \\u041a\\u043e\\u043b\\u0443\\u043c\\u0431\\u0438\\u044f\"], \"17\": [\"Eiji Tsuburaya\", \"Arda Turan\", \"Tom Dumoulin\", \"Bea Meulman\", \"Varoufakis\", \"Renault Talisman\", \"Rafael van der Vaart\", \"Huy\", \"Cancellara\", \"Kourtney Kardashian\", \"Referendum Griekenland\", \"Copa America\", \"Metropolis\", \"buien\", \"Tour de France\", \"beachvolleybal\", \"Nicolette van Dam\", \"Lars Boom\", \"Mumford and Sons\", \"Verstappen\"], \"16\": [\"Eiji Tsuburaya\", \"Arda Turan\", \"Hachim Mastour\", \"Resultat Bac 2015\", \"Resultat Brevet 2015\", \"Renault Talisman\", \"Chute Tour De France\", \"Yanis Varoufakis\", \"Poulidor\", \"Slobodanka Tosic\", \"La Grece\", \"Resultat Bac Pro 2015\", \"Kourtney Kardashian\", \"MasterChef\", \"Vote Grece\", \"Charles Pasqua\", \"Alexis Tsipras\", \"Vampire Diaries\", \"GoPro\", \"Mila Kunis\"], \"19\": [\"Eiji Tsuburaya\", \"Arda Turan\", \"Angelina Jolie\", \"Ppdb Surabaya\", \"Honda Sonic\", \"BPJS Ketenagakerjaan\", \"Marshanda\", \"Lee Si Young\", \"Malam Lailatul Qadar\", \"Kebakaran Di Bandara Soekarno Hatta\", \"Rio Haryanto\", \"Argentina vs Chile\", \"Lee Dong Gun\", \"Gunung Raung\", \"Ariel Tatum\", \"Nuzulul Quran\", \"Prediksi Peru vs Paraguay\", \"Luiz Adriano\", \"Martunis\", \"Ken Ken Wiro Sableng\"], \"18\": [\"Restitui\\u00e7\\u00e3o Imposto De Renda 2015\", \"Feriado 9 De Julho\", \"Cidades de papel\", \"Dia Do Beijo\", \"Tiago Leifert\", \"Daniella Chavez\", \"Prouni 2015\", \"Uber\", \"Mila Kunis\", \"Carol Portaluppi\", \"Arda Turan\", \"Mapas Google\", \"GoPro\", \"Nego do Borel\", \"tabela do Brasileir\\u00e3o 2015\", \"esturj\\u00e3o\", \"Cruzeiro\", \"temperatura\", \"book rosa\", \"Flamengo x Figueirense\"], \"31\": [\"Mila Kunis\", \"Piotr Trojan\", \"ranking FIFA\", \"Radwa\\u0144ska\", \"Arda Turan\", \"Tomasz Lis\", \"Gosia Andrzejewicz\", \"El\\u017cbieta Witek\", \"IMGW\", \"Mapa Burz\", \"Andrzej Kopiczy\\u0144ski\", \"Hope Solo\", \"Renault Talisman\", \"Mistrzostwa \\u015awiata w Pi\\u0142ce No\\u017cnej Kobiet 2015\", \"Gdzie jest burza\", \"Dragon Ball Super\", \"Do Rzeczy\", \"Jacek Mojkowski\", \"burze\", \"S\\u0142ubice\"], \"30\": [\"ABC\", \"9 de Julio\", \"Jonas Gutierrez\", \"Valeria Degenaro\", \"Frida Kahlo\", \"Luisana Lopilato\", \"Mila Kunis\", \"Daniella Chavez\", \"Arda Turan\", \"elecciones\", \"Feriado Puente 10 De Julio\", \"Elecciones 2015\", \"Elecciones 2015 Cordoba\", \"Schiaretti\", \"Grecia\", \"Voto Electronico\", \"Dragon Ball Super\", \"Boleta Electronica\", \"Padron\", \"Elecciones Caba\"], \"37\": [\"Copa America final\", \"Laura Akunga\", \"Huawei P8\", \"Argentina vs Paraguay\", \"Lupita Nyong O\", \"Gmail\", \"KRA\", \"Yahoo\", \"BET AWARDS 2015\", \"You Tube\", \"Google Maps\", \"I Tax\", \"Makari Kituyi\", \"Brazil vs Paraguay\", \"Arturo Vidal\", \"Petr Cech\", \"Fathers Day Messages\", \"Father S Day\", \"Ramadan Kareem\", \"NBA Finals\"], \"36\": [\"\\u0627\\u064a\\u062c\\u064a \\u062a\\u0633\\u0648\\u0628\\u0648\\u0631\\u0627\\u064a\\u0627\", \"\\u0643\\u0648\\u0631\\u0629 \\u0627\\u0648\\u0646 \\u0644\\u0627\\u064a\\u0646\", \"\\u0647\\u064a\\u0641\\u0627\\u0621 \\u062d\\u0633\\u064a\\u0646\", \"\\u0627\\u0631\\u062f\\u0627 \\u062a\\u0648\\u0631\\u0627\\u0646\", \"\\u0635\\u0644\\u0627\\u0629 \\u0627\\u0644\\u062a\\u0647\\u062c\\u062f\", \"\\u0633\\u0639\\u064a\\u062f \\u0627\\u0644\\u0647\\u0648\\u0627\", \"\\u0627\\u0645 \\u0627\\u0644\\u0642\\u0631\\u0649\", \"\\u0627\\u0633\\u0639\\u0627\\u0631 \\u0627\\u0644\\u0639\\u0645\\u0644\\u0627\\u062a\", \"\\u0627\\u0644\\u0639\\u0634\\u0631 \\u0627\\u0644\\u0627\\u0648\\u0627\\u062e\\u0631 \\u0645\\u0646 \\u0631\\u0645\\u0636\\u0627\\u0646\", \"\\u0633\\u0639\\u0631 \\u0627\\u0644\\u062f\\u0648\\u0644\\u0627\\u0631 \\u0641\\u0649 \\u0645\\u0635\\u0631\", \"\\u062d\\u0631\\u0648\\u0641 \\u0648\\u0627\\u0644\\u0648\\u0641\", \"\\u0635\\u0646\\u062f\\u0648\\u0642 \\u0627\\u0644\\u062a\\u0646\\u0645\\u064a\\u0629 \\u0627\\u0644\\u0639\\u0642\\u0627\\u0631\\u064a\\u0629\", \"\\u0627\\u0644\\u0639\\u0634\\u0631 \\u0627\\u0644\\u0627\\u0648\\u0627\\u062e\\u0631\", \"\\u062a\\u0631\\u0643\\u064a \\u0627\\u0644\\u0633\\u062f\\u064a\\u0631\\u064a\", \"\\u0628\\u0627\\u0628 \\u0627\\u0644\\u062d\\u0627\\u0631\\u0629 \\u0627\\u0644\\u062c\\u0632\\u0621 \\u0627\\u0644\\u0633\\u0627\\u0628\\u0639 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 19\", \"\\u0628\\u0627\\u0628 \\u0627\\u0644\\u062d\\u0627\\u0631\\u0629 \\u0627\\u0644\\u062c\\u0632\\u0621 \\u0627\\u0644\\u0633\\u0627\\u0628\\u0639 \\u0627\\u0644\\u062d\\u0644\\u0642\\u0629 18\", \"\\u0627\\u0644\\u0627\\u0631\\u062c\\u0646\\u062a\\u064a\\u0646 \\u0648\\u062a\\u0634\\u064a\\u0644\\u064a\", \"kiva\", \"\\u0644\\u064a\\u0644\\u0629 \\u0627\\u0644\\u0642\\u062f\\u0631\", \"\\u0627\\u0644\\u0627\\u0631\\u062c\\u0646\\u062a\\u064a\\u0646\"], \"35\": [\"\\u0418\\u0432\\u0430\\u043d\\u0430 \\u041a\\u0443\\u043f\\u0430\\u043b\\u0430\", \"\\u0418\\u0432\\u0430\\u043d\\u0430 \\u041a\\u0443\\u043f\\u0430\\u043b\\u0430 2015\", \"\\u041d\\u043e\\u0432\\u043e\\u0441\\u0442\\u0438 \\u0423\\u043a\\u0440\\u0430\\u0438\\u043d\\u0430\", \"\\u041c\\u0430\\u043a\\u0430\\u0440\\u0435\\u0432\\u0438\\u0447\", \"\\u041c\\u0438\\u043d\\u0444\\u0438\\u043d\", \"\\u0414\\u0435\\u043d\\u044c \\u043f\\u043e\\u0446\\u0435\\u043b\\u0443\\u0435\\u0432\", \"\\u041a\\u0443\\u0440\\u0441 \\u041d\\u0431\\u0443\", \"\\u0413\\u041f\\u0423\", \"\\u0410\\u0432\\u0430\\u043b\\u044c\", \"\\u043a\\u0443\\u0440\\u0441\", \"\\u0413\\u0440\\u0435\\u0446\\u0438\\u044f\", \"\\u041f\\u043e\\u043b\\u0438\\u0446\\u0438\\u044f \\u041a\\u0438\\u0435\\u0432\", \"\\u0427\\u0438\\u043b\\u0438 \\u0410\\u0440\\u0433\\u0435\\u043d\\u0442\\u0438\\u043d\\u0430\", \"\\u0417\\u0435\\u043c\\u0444\\u0438\\u0440\\u0430\", \"\\u041a\\u0441\\u0435\\u043d\\u0438\\u044f \\u0411\\u043e\\u0440\\u043e\\u0434\\u0438\\u043d\\u0430\", \"\\u0412\\u043b\\u0430\\u0434\\u0438\\u0441\\u043b\\u0430\\u0432 \\u041b\\u0435\\u0432\\u0438\\u0446\\u044c\\u043a\\u0438\\u0439\", \"\\u0412\\u043b\\u0430\\u0434\\u0438\\u0441\\u043b\\u0430\\u0432 \\u041b\\u0435\\u0432\\u0438\\u0446\\u043a\\u0438\\u0439\", \"\\u0417\\u041d\\u041e\", \"\\u0422\\u043e\\u043c \\u0425\\u0435\\u043d\\u043a\\u0441\", \"World of Warships\"], \"34\": [\"Eiji Tsuburaya\", \"Rozaimi\", \"Greece\", \"wombat\", \"Women S World Cup 2015\", \"Hulk\", \"Copa America final\", \"Lee Dong Gun\", \"Wall Street Journal\", \"Martunis\", \"Astro AWANI\", \"Papanasam\", \"Emilia Clarke\", \"Terminator\", \"Mona Fandey\", \"iOS 8.4\", \"Argentina vs Paraguay\", \"Ben Affleck\", \"Clash of Clans\", \"Cristiano Ronaldo\"], \"33\": [\"\\u0e41\\u0e15\\u0e07\\u0e42\\u0e21\", \"\\u0e40\\u0e01\\u0e23\\u0e0b \\u0e40\\u0e14\\u0e2d\\u0e30\\u0e2a\\u0e15\\u0e32\\u0e23\\u0e4c 6\", \"\\u0e40\\u0e08\\u0e49\\u0e32\\u0e19\\u0e32\\u0e07\", \"\\u0e01\\u0e23\\u0e35\\u0e0b\", \"Maroon 5\", \"\\u0e40\\u0e01\\u0e48\\u0e07 \\u0e40\\u0e21\\u0e18\\u0e31\\u0e2a\", \"\\u0e41\\u0e2b\\u0e25\\u0e01\", \"\\u0e0b\\u0e35 \\u0e28\\u0e34\\u0e27\\u0e31\\u0e12\\u0e19\\u0e4c\", \"\\u0e08\\u0e35\\u0e22\\u0e2d\\u0e19\", \"\\u0e40\\u0e2d\\u0e21\\u0e21\\u0e35\\u0e48 \\u0e21\\u0e23\\u0e01\\u0e15\", \"\\u0e01\\u0e34\\u0e19\\u0e42\\u0e1b\\u0e23\", \"\\u0e42\\u0e21 \\u0e21\\u0e19\\u0e0a\\u0e19\\u0e01\", \"\\u0e1b\\u0e4b\\u0e32\\u0e40\\u0e1b\\u0e23\\u0e21\", \"Bike For Mom\", \"\\u0e42\\u0e1b\\u0e23\\u0e42\\u0e04\\u0e14\\u0e34\\u0e25\", \"\\u0e40\\u0e1a\\u0e19 \\u0e0a\\u0e25\\u0e32\\u0e17\\u0e34\\u0e28\", \"\\u0e01\\u0e23\\u0e21\\u0e17\\u0e35\\u0e48\\u0e14\\u0e34\\u0e19\", \"\\u0e17\\u0e34\\u0e49\\u0e07\\u0e44\\u0e27\\u0e49\\u0e01\\u0e25\\u0e32\\u0e07\\u0e17\\u0e32\\u0e07 - Potato\", \"\\u0e2b\\u0e27\\u0e22\", \"\\u0e40\\u0e25\\u0e02\\u0e40\\u0e14\\u0e47\\u0e14\"], \"32\": [\"Tour de Francia\", \"Dolar Hoy\", \"Movistar\", \"Dropbox\", \"Procuraduria\", \"une\", \"Arda Turan\", \"Proteccion\", \"Contraloria\", \"Daniella Chavez\", \"Portafolio\", \"Se\\u00f1al Colombia\", \"Wimbledon 2015\", \"Camara De Comercio\", \"Frida Kahlo\", \"Copa Mundial Femenina 2015\", \"Donald Trump\", \"Alex Morgan\", \"Jeison Murillo\", \"Grecia\"], \"50\": [\"Ronda Rousey\", \"HJK\", \"Eminem\", \"Deep Rising\", \"Peter Gabriel\", \"6.7\", \"Copa America\", \"Helsinki Cup\", \"Sanni\", \"broken City\", \"Kim Kardashian\", \"Pharrell Williams\", \"Sabrina\", \"Bruce Jenner\", \"F1 Live Stream\", \"Studio Killers\", \"Tour de France\", \"Ellie Goulding\", \"Nicki Minaj\", \"Summer Up 2015\"]}";
        successCaptor.getValue().onResponse(testApiResponse);
        assertEquals(true, keywordGenerator.isSync());
    }

    @Test
    public void testSyncFail() {
        ArgumentCaptor<Response.ErrorListener> errorCaptor = ArgumentCaptor.forClass(Response.ErrorListener.class);
        verify(volleyRequestQueue).sendGetRequest(anyString(), any(Response.Listener.class), errorCaptor.capture());
        errorCaptor.getValue().onErrorResponse(null);
        //TODO: check if has log.
    }

}
