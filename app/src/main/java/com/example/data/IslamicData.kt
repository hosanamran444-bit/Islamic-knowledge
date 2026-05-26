package com.example.data

// Model for a Folder / Category
data class IslamicFolder(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String, // mapped to Material Icons
    val subCategories: List<IslamicSubCategory>
)

// Model for Sub-categories within a Folder
data class IslamicSubCategory(
    val id: String,
    val title: String,
    val contentItems: List<ContentItem>
)

// Model for individual Content Items (e.g., Ayah, Hadith, or Masla)
data class ContentItem(
    val title: String = "",
    val ArabicText: String? = null,
    val BanglaTranslation: String? = null,
    val PronunciationBg: String? = null, // Bangla pronunciation for Arabic text
    val Explanation: String? = null,
    val Reference: String? = null
)

// Bangladesh Division offsets from Dhaka in minutes
data class DivisionInfo(
    val englishName: String,
    val banglaName: String,
    val latitude: Double,
    val longitude: Double,
    val fajrOffset: Int,     // Offset in minutes from Dhaka
    val dhuhrOffset: Int,
    val asrOffset: Int,
    val maghribOffset: Int,
    val ishaOffset: Int
)

object IslamicData {

    // List of Bangladesh Divisions with prayer time offsets (Source: Islamic Foundation/Standard calculations)
    val divisions = listOf(
        DivisionInfo("Dhaka", "ঢাকা", 23.8103, 90.4125, 0, 0, 0, 0, 0),
        DivisionInfo("Chittagong", "চট্টগ্রাম", 22.3569, 91.7832, -5, -4, -3, -5, -5),
        DivisionInfo("Sylhet", "সিলেট", 24.8949, 91.8687, -6, -2, 0, -6, -5),
        DivisionInfo("Rajshahi", "রাজশাহী", 24.3636, 88.6241, 6, 4, 3, 6, 6),
        DivisionInfo("Khulna", "খুলনা", 22.8456, 89.5403, 3, 3, 2, 4, 3),
        DivisionInfo("Barisal", "বরিশাল", 22.7010, 90.3563, 1, 1, 1, 2, 1),
        DivisionInfo("Rangpur", "রংপুর", 25.7508, 89.2467, 4, 3, 1, 5, 6),
        DivisionInfo("Mymensingh", "ময়মনসিংহ", 24.7471, 90.4203, -1, -1, 0, -1, -1)
    )

    // Baseline Prayer Times for Dhaka (which will be adjusted dynamic based on current calendar date and selected division)
    // Real-time calculation helper
    fun getPrayerTimesForDivision(division: DivisionInfo, dayOfYear: Int): Map<String, String> {
        // Base standard prayer hours for Dhaka on an average day, adjusted dynamically for seasonal variations (simplified solar equation)
        val seasonalFactor = kotlin.math.sin(2 * Math.PI * (dayOfYear - 80) / 365.0) * 15.0 // minutes variation
        
        // Base hours in 24-hr format (Dhaka baseline)
        val fajrMinutes = (4 * 60 + 30 + seasonalFactor + division.fajrOffset).toInt()
        val sunriseMinutes = (5 * 60 + 55 + seasonalFactor + division.fajrOffset).toInt()
        val dhuhrMinutes = (12 * 60 + 0 + (seasonalFactor * 0.1) + division.dhuhrOffset).toInt()
        val asrMinutes = (16 * 60 + 20 + (seasonalFactor * 0.5) + division.asrOffset).toInt()
        val maghribMinutes = (18 * 60 + 25 + seasonalFactor + division.maghribOffset).toInt()
        val ishaMinutes = (19 * 60 + 45 + seasonalFactor + division.ishaOffset).toInt()

        fun formatTime(minutes: Int): String {
            val hrs = (minutes / 60) % 24
            val mins = minutes % 60
            val amPm = if (hrs >= 12) "PM" else "AM"
            val displayHrs = if (hrs % 12 == 0) 12 else hrs % 12
            return String.format("%02d:%02d %s", displayHrs, mins, amPm)
        }

        return mapOf(
            "Fajr" to formatTime(fajrMinutes),
            "Sunrise" to formatTime(sunriseMinutes),
            "Dhuhr" to formatTime(dhuhrMinutes),
            "Asr" to formatTime(asrMinutes),
            "Maghrib" to formatTime(maghribMinutes),
            "Isha" to formatTime(ishaMinutes)
        )
    }

    val folders = listOf(
        // 1. কুরআন ফোল্ডার
        IslamicFolder(
            id = "quran",
            title = "কুরআন",
            description = "গুরুত্বপূর্ণ সূরাসমূহের বিশুদ্ধ আরবি ও ইসলামিক ফাউন্ডেশনের বাংলা অনুবাদ",
            iconName = "menu_book",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "sura_fatihah",
                    title = "সূরা আল-ফাতিহা (Sura Al-Fatihah)",
                    contentItems = listOf(
                        ContentItem(
                            title = "আয়াত ১",
                            ArabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            BanglaTranslation = "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে (শুরু করছি)।",
                            PronunciationBg = "বিসমিল্লাহির রহমানির রাহিম।",
                            Explanation = "সূরা আল-ফাতিহা হচ্ছে কুরআনের ভূমিকা এবং অত্যন্ত বরকতময় সূরা। প্রতিটি নামাযের প্রতিটি রাকাতে এটি পাঠ করা ফরজ।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ১ (ইসলামিক ফাউন্ডেশন)"
                        ),
                        ContentItem(
                            title = "আয়াত ২",
                            ArabicText = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                            BanglaTranslation = "যাবতীয় প্রশংসা একমাত্র আল্লাহর জন্য, যিনি সকল সৃষ্টির পালনকর্তা।",
                            PronunciationBg = "আলহামদু লিল্লাহি রাব্বিল আলামিন।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ২"
                        ),
                        ContentItem(
                            title = "আয়াত ৩",
                            ArabicText = "الرَّحْمَٰنِ الرَّحِيمِ",
                            BanglaTranslation = "যিনি পরম করুণাময় ও অতি দয়ালু।",
                            PronunciationBg = "আর-রাহমানির রাহিম।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ৩"
                        ),
                        ContentItem(
                            title = "আয়াত ৪",
                            ArabicText = "مَالِكِ يَوْمِ الدِّينِ",
                            BanglaTranslation = "যিনি বিচার দিবসের অধিপতি।",
                            PronunciationBg = "মালিকি ইয়াওমিদ্দিন।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ৪"
                        ),
                        ContentItem(
                            title = "আয়াত ৫",
                            ArabicText = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
                            BanglaTranslation = "আমরা একমাত্র আপনারই ইবাদত করি এবং একমাত্র আপনার কাছেই সাহায্য প্রার্থনা করি।",
                            PronunciationBg = "ইয়্যাকা না'বুদু ওয়া ইয়্যাকা নাস্তায়িন।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ৫"
                        ),
                        ContentItem(
                            title = "আয়াত ৬",
                            ArabicText = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
                            BanglaTranslation = "আমাদেরকে সরল ও সঠিক পথ প্রদর্শন করুন।",
                            PronunciationBg = "ইহদিনাস সিরাতাল মুস্তাকিম।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ৬"
                        ),
                        ContentItem(
                            title = "আয়াত ৭",
                            ArabicText = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
                            BanglaTranslation = "তাদের পথে, যাদেরকে আপনি নিয়ামত দান করেছেন। তাদের পথে নয় যারা ক্রোধগ্রস্ত এবং যারা পথভ্রষ্ট হয়েছে।",
                            PronunciationBg = "সিরাতাল্লাজিনা আনআমতা আলাইহিম, গাইরিল মাগদুবি আলাইহিম ওয়া লাদ্দল্লিন।",
                            Reference = "সূরা আল-ফাতিহা, আয়াত: ৭"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "sura_ikhlas",
                    title = "সূরা আল-ইখলাস (Sura Al-Ikhlas)",
                    contentItems = listOf(
                        ContentItem(
                            title = "আয়াত ১",
                            ArabicText = "قُلْ هُوَ اللَّهُ أَحَدٌ",
                            BanglaTranslation = "বলুন, তিনিই আল্লাহ, একক ও অদ্বিতীয়।",
                            PronunciationBg = "কুল হুওয়াল্লাহু আহাদ।",
                            Reference = "সূরা আল-ইখলাস, আয়াত: ১"
                        ),
                        ContentItem(
                            title = "আয়াত ২",
                            ArabicText = "اللَّهُ الصَّمَدُ",
                            BanglaTranslation = "আল্লাহ স্বয়ংসম্পূর্ণ এবং মুখাপেক্ষীহীন (সবাই তাঁর মুখাপেক্ষী)।",
                            PronunciationBg = "আল্লাহুচ্ছামাদ।",
                            Reference = "সূরা আল-ইখলাস, আয়াত: ২"
                        ),
                        ContentItem(
                            title = "আয়াত ৩",
                            ArabicText = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
                            BanglaTranslation = "তিনি কাউকে জন্ম দেননি এবং তাঁকেও জন্ম দেয়া হয়নি।",
                            PronunciationBg = "লাম ইয়ালিদ ওয়া লাম ইউলাদ।",
                            Reference = "সূরা আল-ইখলাস, আয়াত: ৩"
                        ),
                        ContentItem(
                            title = "আয়াত ৪",
                            ArabicText = "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
                            BanglaTranslation = "এবং তাঁর সমকক্ষ বা সমতুল্য কেউই নেই।",
                            PronunciationBg = "ওয়া লাম ইয়াকুল্লাহু কুফুয়ান আহাদ।",
                            Explanation = "সূরা আল-ইখলাস তাওহীদের মূল দর্শন। রাসূলুল্লাহ (সা.) বলেছেন, এটি পাঠ করলে কুরআনের এক-তৃতীয়াংশ পাঠের সওয়াব পাওয়া যায়।",
                            Reference = "সূরা আল-ইখলাস, আয়াত: ৪"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "ayat_kursi",
                    title = "আয়াতুল কুরসী (Ayat Al-Kursi)",
                    contentItems = listOf(
                        ContentItem(
                            title = "সুরা বাকারাহ - আয়াত ২৫৫",
                            ArabicText = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                            BanglaTranslation = "আল্লাহ, তিনি ছাড়া কোনো সত্যিকারের উপাস্য নেই, তিনি চিরঞ্জীব, সর্বসত্তার ধারক। তাঁকে তন্দ্রা ও নিদ্রা স্পর্শ করে না। আসমান ও জমিনে যা কিছু আছে সব তাঁরই। কে সে, যে তাঁর অনুমতি ছাড়া তাঁর কাছে সুপারিশ করবে? তাদের সামনে ও পিছনে যা কিছু আছে তা তিনি জানেন। আর তাঁর জ্ঞানের সামান্যতম অংশও তারা আয়ত্ত করতে পারে না, তবে তিনি যতটুকু ইচ্ছা করেন তা ছাড়া। তাঁর কুরসী সমস্ত আসমান ও জমিন পরিব্যাপ্ত করে আছে। আর এতদুভয়ের রক্ষণাবেক্ষণ তাঁকে ক্লান্ত করে না। তিনি সুউচ্চ, মহামহিম।",
                            PronunciationBg = "আল্লাহু লা ইলাহা ইল্লা হুয়াল হাইয়ুল কাইয়ুম। লা তা’খুযুহু সিনাতুও ওয়ালা নাউম। লাহু মা ফিসসামাওয়াতি ওয়ামা ফিল আরদ। মান যাল্লাযী ইয়াশফাউ ‘ইনদাহু ইল্লা বিইযনিহ। ইয়া’লামু মা বাইনা আইদীহীম ওয়ামা খালফাহুম। ওয়ালা ইয়ুহীতূনা বিশাইইম মিন ‘ইলমিহী ইল্লা বিমা শা-আ। ওয়াসি‘আ কুরসিয়্যুহুস সামাওয়াতি ওয়াল আরদ, ওয়ালা ইয়াউদুহু হিফযুহুমা, ওয়া হুয়াল ‘আলিইয়ুল ‘আযীম।",
                            Explanation = "কুরআনের সবচেয়ে মহিমান্বিত আয়াত। ঘুমানোর পূর্বে এটি পাঠ করলে নিয়োজিত ফেরেশতা সারারাত হেফাজত করে এবং শয়তান কাছে আসতে পারে না।",
                            Reference = "সূরা আল-বাকারাহ, আয়াত: ২৫৫"
                        )
                    )
                )
            )
        ),

        // 2. হাদীস ফোল্ডার
        IslamicFolder(
            id = "hadith",
            title = "হাদীস",
            description = "সহীহ বুখারী এবং মুসলিম শরীফ হতে সংকলিত বরকতময় বাণীসমূহ",
            iconName = "forum",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "hadith_niyah",
                    title = "নিয়ত এবং আমল (Hadith on Sincerity)",
                    contentItems = listOf(
                        ContentItem(
                            title = "নিয়তের গুরুত্ব",
                            ArabicText = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
                            BanglaTranslation = "নিশ্চয়ই সমস্ত আমল নিয়ত বা সংকল্পের ওপর নির্ভরশীল। আর প্রত্যেক ব্যক্তি তার নিয়ত অনুসারেই ফল পাবে।",
                            PronunciationBg = "ইন্নামাল আ'মালু বিন-নিইয়াত, ওয়া ইন্নামা লিকুল্লিমরিইম মা নাওয়া।",
                            Explanation = "উমর ইবনুল খাত্তাব (রা.) থেকে বর্ণিত। এটি সহীহ বুখারী ও মুসলিমের প্রথম হাদিস। ইসলামে যেকোনো ইবাদত কবুল হওয়ার জন্য খাঁটি নিয়ত বা আল্লাহকে সন্তুষ্ট করার ইচ্ছা পূর্বশর্ত।",
                            Reference = "সহীহ আল-বুখারী, হাদিস নং: ১"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "hadith_character",
                    title = "উত্তম চরিত্র (Hadith on Akhlaq/Character)",
                    contentItems = listOf(
                        ContentItem(
                            title = "চরিত্রের মহত্ত্ব",
                            ArabicText = "إِنَّ مِنْ أَخْيَرِكُمْ أَحْسَنَكُمْ أَخْلاقًا",
                            BanglaTranslation = "নিশ্চয়ই তোমাদের মধ্যে সর্বাপেক্ষা উত্তম ব্যক্তি সে, যার চরিত্র ও আচরণ সবচেয়ে সুন্দর।",
                            PronunciationBg = "ইন্না মিন আখইয়ারিকুম আহসানাকুম আখলাকা।",
                            Explanation = "আব্দুল্লাহ ইবনে আমর (রা.) থেকে বর্ণিত। একজন মুমিনের প্রধান অলঙ্কার হচ্ছে তার সুন্দর আচরণ, বিনম্রতা ও সত্যবাদিতা।",
                            Reference = "সহীহ আল-বুখারী, হাদিস নং: ৩৫৫৯"
                        ),
                        ContentItem(
                            title = "পরোপকার ও মুমিন",
                            ArabicText = "لاَ يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
                            BanglaTranslation = "তোমাদের কেউ প্রকৃত মুমিন হতে পারবে না যতক্ষণ না সে নিজের জন্য যা পছন্দ করে, তার মুসলিম ভাইয়ের জন্যও তা-ই পছন্দ করে।",
                            PronunciationBg = "লা ইউ'মিনু আহাদুকুম হাত্তা ইয়ুহিব্বা লিআখীহি মা ইয়ুহিব্বু লিনাফসিহ।",
                            Reference = "সহীহ আল-বুখারী, হাদিস নং: ১৩"
                        )
                    )
                )
            )
        ),

        // 3. ঈমান ফোল্ডার
        IslamicFolder(
            id = "iman",
            title = "ঈমান",
            description = "ইসলামের মৌলিক বিশ্বাসের স্তম্ভসমূহ এবং কালেমাগুলোর সঠিক অর্থ",
            iconName = "workspace_premium",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "kalimas",
                    title = "কালেমাসমূহ (Five Kalimas)",
                    contentItems = listOf(
                        ContentItem(
                            title = "১. কালেমা তায়্যিবা (Kalima Tayyibah)",
                            ArabicText = "لَا إِلٰهَ إِلَّا اللهُ مُحَمَّدٌ رَسُولُ اللهِ",
                            BanglaTranslation = "আল্লাহ ছাড়া কোনো সত্য উপাস্য নেই, হযরত মুহাম্মদ (সা.) আল্লাহর প্রেরিত রসূল।",
                            PronunciationBg = "লা ইলাহা ইল্লাল্লাহু মুহাম্মাদুর রাসুলুল্লাহ।",
                            Reference = "ঈমানের প্রথম মূলভিত্তি"
                        ),
                        ContentItem(
                            title = "২. কালেমা শাহাদাত (Kalima Shahadat)",
                            ArabicText = "أَشْهَدُ أَنْ لَا إِلٰهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
                            BanglaTranslation = "আমি সাক্ষ্য দিচ্ছি যে, আল্লাহ ছাড়া কোনো উপাস্য নেই। তিনি একক, তাঁর কোনো অংশীদার নেই। আমি আরও সাক্ষ্য দিচ্ছি যে, নিশ্চয়ই মুহাম্মদ (সা.) তাঁর বান্দা ও তাঁর প্রেরিত রসূল।",
                            PronunciationBg = "আশহাদু আল লা ইলাহা ইল্লাল্লাহু ওয়াহদাহু লা শারীকা লাহু, ওয়া আশহাদু আন্না মুহাম্মাদান আবদুহু ওয়া রাসুলুহু।",
                            Reference = "ঈমানের সাক্ষ্যদানকারী বাক্য"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "iman_pillars",
                    title = "ঈমানের মূল স্তম্ভসমূহ (Pillars of Iman)",
                    contentItems = listOf(
                        ContentItem(
                            title = "ঈমানের ৭টি স্তম্ভ (সংক্ষিপ্ত বিবরণ)",
                            Explanation = "১. আল্লাহর প্রতি বিশ্বাস (একত্ববাদ বা তাওহীদ)।\n২. আল্লাহর ফেরেশতাদের ওপর বিশ্বাস।\n৩. আল্লাহর নাযিলকৃত কিতাব বা গ্রন্থসমূহের ওর বিশ্বাস।\n৪. আল্লাহর সকল নবী ও রাসূলগণের ওপর বিশ্বাস।\n৫. কিয়ামত বা পরকালের ওপর বিশ্বাস।\n৬. তকদীর বা ভালো-মন্দের ওপর বিশ্বাস যা আল্লাহর পক্ষ থেকে নির্ধারিত।\n৭. মৃত্যুর পর পুনরুত্থানের ওপর বিশ্বাস।",
                            Reference = "সুরা বাকারাহ আয়াত ২৮৫ ও হাদীসে জিবরাঈল"
                        )
                    )
                )
            )
        ),

        // 4. নামাজ ফোল্ডার
        IslamicFolder(
            id = "namaz",
            title = "নামাজ",
            description = "নামাজের ফরজ, ওয়াজিব ও সুন্নত নিয়ম এবং নিয়মাবলী",
            iconName = "accessibility_new",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "namaz_fard",
                    title = "নামাজের রোকন ও ফরজসমূহ (Farz of Namaz)",
                    contentItems = listOf(
                        ContentItem(
                            title = "আহকাম বা নামাজের বাইরের ৭টি ফরজ",
                            Explanation = "১. শরীর পবিত্র হওয়া।\n২. কাপড় বা পরিধেয় বস্ত্র পবিত্র হওয়া।\n৩. নামাজ পড়ার স্থান বা জায়গা পবিত্র হওয়া।\n৪. সতর বা ঢেকে রাখার অঙ্গ আবৃত করা (পুরুষের নাভি থেকে হাঁটু পর্যন্ত, নারীর মুখ ও দুই হাত বাদে সমস্ত শরীর)।\n৫. কিবলামুখী হওয়া।\n৬. ওয়াক্ত অনুযায়ী বা সময়মত নামাজ পড়া।\n৭. নামাজের নিয়ত করা অনুভব করা।"
                        ),
                        ContentItem(
                            title = "আরকান বা নামাজের ভেতরের ৭টি ফরজ",
                            Explanation = "১. তাকবীরে তাহরীমা ('আল্লাহু আকবার' বলে নামাজ শুরু করা)।\n২. দাঁড়িয়ে নামাজ পড়া (অক্ষম হলে বসে পড়ার বিধান আছে)।\n৩. কিরাত বা কুরআন তিলাওয়াত করা।\n৪. রুকু করা।\n৫. দুই সেজদা করা।\n৬. শেষ বৈঠকে বসা (তাশাহহুদের সমপরিমাণ সময় বসা)।\n৭. সালাম ফিরানোর মাধ্যমে নামাজ সমাপ্ত করা।"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "namaz_steps",
                    title = "নামাজ আদায়ের সংক্ষিপ্ত নিয়ম",
                    contentItems = listOf(
                        ContentItem(
                            title = "সহজ ধাপসমূহ",
                            Explanation = "১. নামাজের স্থানে সোজা হয়ে দাঁড়াবেন।\n২. মনে মনে নিয়ত করে হাত কান পর্যন্ত (নারীদের কাঁধ পর্যন্ত) উঠিয়ে 'আল্লাহু আকবার' বলে নাভির নিচে (নারীদের বুকের উপর) হাত বাঁধবেন।\n৩. ছানা (সুবহানাকাল্লাহুম্মা...) পড়বেন।\n৪. সূরা ফাতিহা পাঠ করে অন্য একটি সূরা মেলাবেন।\n৫. 'আল্লাহু আকবার' বলে রুকুতে যাবেন। রুকুতে তাসবীহ পড়বেন ৩ বার।\n৬. রুকু থেকে সোজা হয়ে দাঁড়াবেন (সামিয়াল্লাহু লিমান হামিদাহ, রাব্বানা লাকাল হামদ)।\n৭. 'আল্লাহু আকবার' বলে সেজদায় যাবেন। সেজদার তাসবীহ তিনবার পড়বেন। এভাবে দুই সেজদা দিয়ে পরবর্তী রাকাত শুরু করবেন।"
                        )
                    )
                )
            )
        ),

        // 5. রোজা ফোল্ডার
        IslamicFolder(
            id = "roza",
            title = "রোজা",
            description = "রোজার ফজিলত, নিয়ম, সেহরি ও ইফতারের দোয়া সমূহ",
            iconName = "wb_sunny",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "roza_duas",
                    title = "সেহরি ও ইফতারের দোয়া (Ramadan Duas)",
                    contentItems = listOf(
                        ContentItem(
                            title = "সেহরির দোয়া (নিয়ত)",
                            ArabicText = "نَوَيْتُ أَنْ أَصُومَ غَدًا مِّنْ شَهْرِ رَمَضَانَ الْمُبَارَكِ فَرْضًا لَكَ يَا اللَّهُ فَتَقَبَّلْ مِنِّي إِنَّكَ أَنْتَ السَّمِيعُ الْعَلِيمُ",
                            BanglaTranslation = "হে আল্লাহ! আগামীকাল পবিত্র রমজান মাসে তোমার উদ্দেশ্যে ফরয রোজা রাখার নিয়ত করছি। তুমি আমার পক্ষ থেকে তা কবুল করো। নিশ্চয়ই তুমি সর্বশ্রোতা, সর্বজ্ঞ।",
                            PronunciationBg = "নাওয়াইতু আন আসুমা গাদাম মিন শাহরি রামাদ্বানাল মুবারাকি ফারদাল্লাকা ইয়া আল্লাহু ফাতাকাব্বাল মিন্নি ইন্নাকা আনতাস সামিউল আলিম।",
                            Reference = "নফল ও রমজানের নিয়ত মনের ইচ্ছা দ্বারাও বিশুদ্ধ হয়ে যায়।"
                        ),
                        ContentItem(
                            title = "ইফতারের দোয়া",
                            ArabicText = "اللَّهُمَّ لَكَ صُمْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ",
                            BanglaTranslation = "হে আল্লাহ! আমি তোমারই সন্তুষ্টির জন্য রোজা রেখেছি এবং তোমার দেওয়া রিযিক দ্বারাই ইফতার করছি।",
                            PronunciationBg = "আল্লাহুম্মা লাকা সুমতু ওয়া আলা রিযক্বিকা আফতারতু।",
                            Explanation = "ইফতারের ঠিক সাথে সাথে এই দোয়াটি পাঠ করতে হয়। এছাড়াও অন্য বর্ণিত দোয়াসমূহ পাঠ করা যায়।",
                            Reference = "সুনানে আবু দাউদ, হাদিস নং: ২৩৫৮"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "roza_breakers",
                    title = "রোজার মাসায়েল ও ভঙ্গের কারণসমূহ",
                    contentItems = listOf(
                        ContentItem(
                            title = "যেসব কারণে রোজা ভেঙে যায় বা নষ্ট হয়",
                            Explanation = "১. ইচ্ছাকৃতভাবে পানাহার (খাবার বা পানি) গ্রহণ করলে।\n২. ইচ্ছাকৃতভাবে মুখ ভরে বমি করলে।\n৩. স্ত্রী সহবাস বা যৌন মিলন লিপ্ত হলে।\n৪. দাঁত থেকে নির্গত রক্ত গিলে ফেললে যদি রক্তের পরিমাণ থুতুর চেয়ে বেশি হয়।\n৫. ভুলবশত খেয়ে ফেলার পর রোজা ভেঙে গেছে ভেবে পুনরায় ইচ্ছাকৃতভাবে কিছু খেলে।"
                        ),
                        ContentItem(
                            title = "যেসব কারণে রোজা ভাঙে না",
                            Explanation = "১. অনিচ্ছাকৃতভাবে বা ভুলবশত পানি অথবা খাবার খেয়ে ফেললে এবং মনে পড়ার সাথে সাথেই খাওয়া বন্ধ করলে।\n২. চোখে সুরমা বা ড্রপ ব্যবহার করলে।\n৩. শরীরে তেল, সুগন্ধি বা আতর লাগালে।\n৪. অনিচ্ছাকৃতভাবে ধুলো-বালি, মশা-মাছি পেটে চলে গেলে।"
                        )
                    )
                )
            )
        ),

        // 6. হজ্ব ফোল্ডার
        IslamicFolder(
            id = "hajj",
            title = "হজ্ব",
            description = "হজ্ব পালনের অত্যাবশ্যকীয় রুকন এবং হজের নিয়মসমূহ",
            iconName = "location_city",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "hajj_fard",
                    title = "হজের ফরজসমূহ (Farz of Hajj)",
                    contentItems = listOf(
                        ContentItem(
                            title = "হজের ফরজ ৩টি",
                            Explanation = "১. এহরাম পরিধান করা বা হজ্বের নিয়ত করা।\n২. ৯ই জিলহজ্ব মক্কায় আরাফাতের ময়দানে অবস্থান করা (উকুফে আরাফাহ)।\n৩. ১০ই জিলহজ্ব ভোর থেকে শুরু করে জিলহজের ১২ তারিখের মধ্যে বাইতুল্লাহর তাওয়াফে যিয়ারত করা।"
                        ),
                        ContentItem(
                            title = "হজের ওয়াজিবসমূহ",
                            Explanation = "১. সাফা ও মারওয়া পাহাড়ের মাঝে সাই করা বা দৌড়ানো।\n২. মোযদালিফায় ৯ই জিলহজ্ব দিবাগত রাতে অবস্থান করা।\n৩. মিনায় শয়তানকে পাথর (কঙ্কর) নিক্ষেপ করা।\n৪. কুরবানী করা (হজের কোরবানি)।\n৫. মাথা মুণ্ডানো বা চুল কেটে ছোট করা।\n৬. বহিরাগতদের জন্য বিদায়ী তাওয়াফ সম্পন্ন করা।"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "talbiyah",
                    title = "হজ্বের তালবিয়াহ (Talbiyah of Hajj)",
                    contentItems = listOf(
                        ContentItem(
                            title = "তালবিয়াহ",
                            ArabicText = "لَبَّيْكَ اللَّهُمَّ لَبَّيْكَ ، لَبَّيْكَ لَا شَرِيكَ لَكَ لَبَّيْكَ ، إِنَّ الْحَمْدَ وَالنِّعْمَةَ لَكَ وَالْمُلْكَ ، لَا شَرِيكَ لَكَ",
                            BanglaTranslation = "আমি উপস্থিত হয়েছি হে আল্লাহ, আমি উপস্থিত হয়েছি। আমি হাজির হয়েছি, তোমার কোনো শরীক নেই, আমি উপস্থিত হয়েছি। নিশ্চয়ই যাবতীয় প্রশংসা, নিয়ামত এবং রাজত্ব একমাত্র তোমারই। তোমার কোনো অংশীদার নেই।",
                            PronunciationBg = "লাব্বাইকাল্লা-হুম্মা লাব্বাইক, লাব্বাইকা লা- শারীকা লাকা লাব্বাইক, ইন্নাল হামদা ওয়ান্নি'মাতা লাকা ওয়াল মুলক, লা- শারীকা লাক।",
                            Reference = "সহীহ আল-বুখারী"
                        )
                    )
                )
            )
        ),

        // 7. যাকাত ফোল্ডার
        IslamicFolder(
            id = "zakat",
            title = "যাকাত",
            description = "যাকাতের নেসাব এবং যাকাতের গাণিতিক নিয়মাবলী",
            iconName = "payments",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "zakat_rules",
                    title = "যাকাত আদায়ের নিয়ম ও নেসাব (Nisab)",
                    contentItems = listOf(
                        ContentItem(
                            title = "নেসাব এর পরিমাণ",
                            Explanation = "বছরের অতিরিক্ত সম্পদ সাড়ে সাত তোলা বা ভরি সোনা (৮৭.৪৫ গ্রাম) অথবা সাড়ে বায়ান্ন তোলা রূপা (৬১২.৩৬ গ্রাম) অথবা এর সমমূল্যের নগদ অর্থ বা ব্যবসা পণ্যের মালিক হওয়া। এই পরিমাণ সম্পদ পূর্ণ এক বছর মালিকানাধীন থাকলে তাকে নেসাবের মালিক বলা হয় এবং তার উপর ২.৫% (শতকরা আড়াই টাকা) যাকাত দেওয়া ফরয।"
                        ),
                        ContentItem(
                            title = "কারা যাকাত পাওয়ার উপযুক্ত?",
                            Explanation = "১. দরিদ্র বা ফকির (যাদের কোনো সম্পদ নেই)।\n২. মিসকিন (যাদের সামান্য আয় আছে কিন্তু সংসার চলে না)।\n৩. যাকাত বিভাগে নিয়োজিত কর্মকর্তা-কর্মচারীবৃন্দ।\n৪. নওমুসলিম বা ইসলামের প্রতি অনুরাগী ব্যক্তি।\n৫. দাসমুক্তির জন্য।\n৬. ঋণগ্রস্ত ব্যক্তি (ঋণ পরিশোধে অক্ষম)।\n৭. আল্লাহর রাস্তায় নিয়োজিত জিহাদকারী বা মুসাফির।\n৮. সম্বলহীন মুসাফির বা পথচারী।"
                        )
                    )
                )
            )
        ),

        // 8. আমল আখলাক ফোল্ডার
        IslamicFolder(
            id = "amal_akhlaq",
            title = "আমল আখলাক",
            description = "প্রতিদিনের আমলসমূহ, সকাল সন্ধ্যার দোয়া এবং উত্তম আচরণ",
            iconName = "favorite",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "daily_azkar",
                    title = "দৈনিক প্রয়োজনীয় ও ছোট আমল",
                    contentItems = listOf(
                        ContentItem(
                            title = "ঘুমানোর পূর্বের দোয়া (Sleeping Dua)",
                            ArabicText = "اللَّهُمَّ بِاسْمِكَ أَمُوتُ وَأَحْيَا",
                            BanglaTranslation = "হে আল্লাহ! আপনারই নাম নিয়ে আমি মৃত্যুবরণ করছি (ঘুমাচ্ছি) এবং আপনারই নামে পুনর্জীবিত (জাগ্রত) হব।",
                            PronunciationBg = "আল্লাহুম্মা বিইছমিকা আমুতু ওয়া আহয়া।",
                            Reference = "সহীহ বুখারী, হাদিস নং: ৬৩২৪"
                        ),
                        ContentItem(
                            title = "ঘুম থেকে ওঠার দোয়া",
                            ArabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
                            BanglaTranslation = "যাবতীয় প্রশংসা আল্লাহর জন্য, যিনি আমাদেরকে মৃত্যু (ঘুম) দেওয়ার পর পুনরায় জীবিত করলেন এবং তাঁর দিকেই আমাদের ফিরে যেতে হবে।",
                            PronunciationBg = "আলহামদু লিল্লাহিল্লাজী আহইয়ানা বা’দা মা আমারতানা ওয়া ইলাইহিন নুশুর।",
                            Reference = "সহীহ বুখারী, হাদিস নং: ৬৩১৪"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "charity_hadith",
                    title = "উত্তম শিষ্টাচার ও বিনম্র আচরণ",
                    contentItems = listOf(
                        ContentItem(
                            title = "মুচকি হাসিও সাদাকাহ",
                            ArabicText = "تَبَسُّمُكَ فِي وَجْهِ أَخِيكَ لَكَ صَدَقَةٌ",
                            BanglaTranslation = "তোমার ভাইয়ের মুখের সামনে হাসা বা মুচকি হাসা তোমার জন্য একটি সাদাকাহস্বরূপ।",
                            PronunciationBg = "তাবাসসুমুকা ফী ওয়াজহি আখীকা লাকা সাদাক্বাহ।",
                            Explanation = "উত্তম আখলাকের চমৎকার শিক্ষা। মানুষের সাথে সুন্দর হাসিমুখে কথা বলাও ইসলামে পুণ্যময় ইবাদত হিসেবে গণ্য হয়।",
                            Reference = "জামে আত-তিরমিযী, হাদিস নং: ১৯৫৬"
                        )
                    )
                )
            )
        ),

        // 9. নারী বিভাগ ফোল্ডার
        IslamicFolder(
            id = "women_section",
            title = "নারী বিভাগ",
            description = "ইসলামে নারীর অধিকার, মর্যাদা এবং পর্দা সংক্রান্ত প্রয়োজনীয় মাসাঈল",
            iconName = "female",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "women_status",
                    title = "ইসলামে নারীর মর্যাদা ও অধিকার",
                    contentItems = listOf(
                        ContentItem(
                            title = "মায়ের পদতলে জান্নাত (Hadith)",
                            ArabicText = "الْجَنَّةُ تَحْتَ أَقْدَامِ الأُمَّهَاتِ",
                            BanglaTranslation = "জান্নাত হচ্ছে মায়েদের কদমের নিচে।",
                            PronunciationBg = "আল-জান্নাতু তাহতা আক্বদামিল উম্মাহাত।",
                            Explanation = "মায়ের প্রতি সর্বোচ্চ সম্মান, আনুগত্য ও শ্রদ্ধাশীল হওয়ার জন্য রসূলুল্লাহ (সা.) এই অমিয় বাণী ব্যক্ত করেছেন।",
                            Reference = "সুনানে নাসায়ী, তাবারানী"
                        ),
                        ContentItem(
                            title = "নারীদের উত্তম ব্যবহার ও অধিকার",
                            ArabicText = "خَيْرُكُمْ خَيْرُكُمْ لِأَهْلِهِ وَأَنَا خَيْرُكُمْ لِأَهْلِي",
                            BanglaTranslation = "তোমাদের মধ্যে সর্বোত্তম ব্যক্তি সে, যে তার পরিবারের কাছে (বিশেষ করে স্ত্রীর কাছে) উত্তম। আর আমি আমার স্ত্রীদের কাছে সর্বোত্তম।",
                            PronunciationBg = "খাইরুকুম খাইরুকুম লিআহলিহী ওয়া আনা খাইরুকুম লিআহলী।",
                            Reference = "জামে আত-তিরমিযী, হাদিস নং: ৩৮৯৫"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "hijab_rules",
                    title = "পর্দা বা হিজাবের মৌলিক নিয়মাবলী",
                    contentItems = listOf(
                        ContentItem(
                            title = "পর্দার গুরুত্ব",
                            Explanation = "ইসলাম নারীদের সতীত্ব ও ইজ্জতের সুরক্ষার স্বার্থে হিজাব বা শরিয়তসম্মত পর্দা ফরয করেছে। পরপুরুষ বা গায়রে মাহরাম (যাদের সাথে বিয়ে বৈধ) পুরুষদের সামনে সতর ঢাকা রাখা আবশ্যক। মাহরাম পুরুষদের তালিকা কুরআন মজিদের সূরা নূরের ৩০ ও ৩১ নং আয়াতে স্পষ্টভাবে দেওয়া হয়েছে।"
                        )
                    )
                )
            )
        ),

        // 10. মাসাঈল ফোল্ডার
        IslamicFolder(
            id = "masail",
            title = "মাসাঈল",
            description = "দৈনন্দিন ওযু, গোসল, অপবিত্রতা ও নামাজের সাধারণ সমাধানসমূহ",
            iconName = "help_outline",
            subCategories = listOf(
                IslamicSubCategory(
                    id = "wudu_masail",
                    title = "ওযু এবং গোসলের জরুরী মাসয়ালা",
                    contentItems = listOf(
                        ContentItem(
                            title = "ওযুর ফরযসমূহ (Wudu Farz)",
                            Explanation = "ওযুর ফরয মোট ৪টি:\n১. মুখমণ্ডল একবার সম্পূর্ণ ধৌত করা (কপালের ওপর চুলের গোড়া থেকে থুতনির নিচ এবং এক কানের লতি থেকে অন্য কানের লতি পর্যন্ত)।\n২. উভয় হাত কনুইসহ একবার ধৌত করা।\n৩. মাথার চারভাগের একভাগ মাসেহ করা।\n৪. উভয় পা গোড়ালি বা টাখনুসহ একবার ধৌত করা।"
                        ),
                        ContentItem(
                            title = "গোসলের ফরযসমূহ (Ghusl Farz)",
                            Explanation = "গোসলের ফরয ৩টি:\n১. কুলি করা (এমনভাবে গড়গড়া করা যেন গলার ভেতর পানি পৌঁছায়)।\n২. নাকে পানি দিয়ে নরম অংশ পর্যন্ত পরিষ্কার করা।\n৩. মাথায় পানি ঢেলে সমস্ত শরীর এমনভাবে ধৌত করা যেন একটি চুলের গোড়াও শুকনো না থাকে।"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "tayammum",
                    title = "তায়াম্মুম করার নিয়ম (Tayammum)",
                    contentItems = listOf(
                        ContentItem(
                            title = "পবিত্র মাটির মাধ্যমে তায়াম্মুম",
                            Explanation = "পানির অনুপস্থিতিতে অথবা রোগে আক্রান্ত হয়ে পানি ব্যবহারে অক্ষম হলে পবিত্র মাটি দিয়ে তায়াম্মুম করে নামায আদায় করা যায়।\nধাপ ১: মনে মনে তায়াম্মুমের নিয়ত করবেন।\nধাপ ২: দুই হাতের তালু পবিত্র মাটিতে স্পর্শ করে ফুঁ দিয়ে সমস্ত মুখমণ্ডল বোলাবেন।\nধাপ ৩: পুনরায় মাটিতে হাত মেরে কনুইসহ উভয় হাত মাসেহ করবেন।"
                        )
                    )
                ),
                IslamicSubCategory(
                    id = "qurbani_masail",
                    title = "কুরবানির মাসাঈল (Hanafi Madhhab)",
                    contentItems = listOf(
                        ContentItem(
                            title = "১. কুরবানি কার ওপর ওয়াজিব?",
                            Explanation = "হানাফি মাযহাব অনুযায়ী, ১০ জিলহজ সুবহে সাদেক থেকে ১২ জিলহজ সূর্যাস্ত পর্যন্ত সময়ের মধ্যে যে প্রাপ্তবয়স্ক, সুস্থমস্তিষ্ক মুসলিম ব্যক্তি নেসাব পরিমাণ সম্পদের মালিক হবে, তার ওপর কুরবানি করা ওয়াজিব।\n\nনেসাব ও তার পরিমাণ:\nস্বীয় নিত্যপ্রয়োজনীয় খরচের অতিরিক্ত সাড়ে সাত ভরি সোনা, সাড়ে বায়ান্ন ভরি রূপা অথবা এর সমমূল্যের নগদ টাকা বা ব্যবসায়িক সম্পদ থাকলে তাকে নেসাবের মালিক বলা হয়।"
                        ),
                        ContentItem(
                            title = "২. কুরবানির পশু ও শরিকানা (অংশীদারিত্ব)",
                            Explanation = "উট, গরু, মহিষ, ছাগল, ভেড়া ও দুম্বা দ্বারা কুরবানি করা বৈধ।\n\nশরিক ও অংশের নিয়মাবলী:\n১. ছাগল, ভেড়া ও দুম্বা কেবল একজনের পক্ষ থেকেই কুরবানি করা যায়। এগুলোতে কোনো শরিকানা চলে না।\n২. উট, গরু ও মহিষে সর্বোচ্চ ৭ জন অংশীদার বা শরিক হতে পারবেন।\n৩. অত্যন্ত প্রয়োজনীয় শর্ত: অবশ্যই সকল শরিকের নিয়ত আল্লাহ তাআলার সন্তুষ্টি বা কুরবানি দেওয়ার মনঃস্থির হতে হবে। কারও নিয়ত কেবল গোশত খাওয়া বা লোকদেখানো দম্ভ হলে কারও কুরবানিই কবুল হবে না।"
                        ),
                        ContentItem(
                            title = "৩. পশুর বয়স ও সুস্থতা",
                            Explanation = "কুরবানির পশু নিখুঁত, ত্রুটিমুক্ত ও সুস্থ হওয়া আবশ্যক।\n\nন্যূনতম বয়সসীমা:\n১. উট: ৫ বছর সম্পন্ন হতে হবে।\n২. গরু ও মহিষ: ২ বছর সম্পন্ন হতে হবে।\n৩. ছাগল, ভেড়া ও দুম্বা: ১ বছর সম্পন্ন হতে হবে। তবে ৬ মাসের বেশি বয়সের ভেড়া বা দুম্বা যদি দেখতে ও আকারে ১ বছর বয়সীর মতো স্বাস্থ্যবান মনে হয়, তাহলে তা দ্বারা কুরবানি জায়েজ।\n\nযেসব ত্রুটির কারণে কুরবানি হবে না:\n১. অন্ধত্ব বা স্পষ্ট দেখতে না পাওয়া।\n২. চরম রুগ্ন বা অতিরিক্ত দুর্বল হওয়া।\n৩. এমন খোঁড়া যে নিজের পায়ে ভর দিয়ে জবাই করার স্থানে হেঁটে যেতে পারে না।\n৪. কান বা লেজের এক-তৃতীয়াংশ বা তার বেশি অংশ কাটা যাওয়া।\n৫. শিং গোড়া থেকে উপড়ে যাওয়া বা ভেঙে যাওয়া (যার কারণে মগজ ক্ষতিগ্রস্ত হয়েছে)।"
                        ),
                        ContentItem(
                            title = "৪. জবাইয়ের সুন্নাত তরিকা ও অন্যান্য মাসআলা",
                            Explanation = "১. পশুকে বাম কাতে কিবলামুখী করে শোয়ানো সুন্নাত।\n২. দয়া ও দ্রুততার সাথে তীক্ষ্ণ ধারালো ছুরি দিয়ে জবাই সম্পন্ন করতে হবে যেন পশু অধিক কষ্ট না পায়।\n৩. জবাইয়ের সময় মুখে 'বিসমিল্লাহি আল্লাহু আকবার' (بِسْمِ اللَّهِ اللَّهُ أَكْبَرُ) বলতে হবে। হানাফি মাযহাব মতে, ইচ্ছাকৃতভাবে তাসমিয়াহ (বিসমিল্লাহ) ছেড়ে দিলে পশুর গোশত খাওয়া হালাল হবে না (তবে ভুলবশত বাদ পড়লে কবুল হবে)।\n৪. কসাই, চামড়া ছাড়ানো বা মাংস কাটার কাজে নিয়োজিত শ্রমিকদের পারিশ্রমিক হিসেবে কুরবানির মাংস বা চামড়া দেওয়া যাবে না। পারিশ্রমিক আলাদা অর্থ বা সম্পদ হিসেবে প্রদান করতে হবে।"
                        )
                    )
                )
            )
        )
    )
}
