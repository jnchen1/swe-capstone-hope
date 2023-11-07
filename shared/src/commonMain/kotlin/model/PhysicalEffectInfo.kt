package model

enum class PhysicalEffectInfo(
    val icon: String,
    val portraitInfo: String,
    val landscapeInfo: String,
    val description: String,
    val title: String
) {
    SURGERY(
        "physical_effect/pe_surgery_icon.png",
        "physical_effect/pe_surgery_info_portrait.png",
        "physical_effect/pe_surgery_info_landscape.png",
        "Surgery",
        "Surgery"
    ),
    RADIOTHERAPY(
        "physical_effect/pe_radiotherapy_icon.png",
        "physical_effect/pe_radiotherapy_info_portrait.png",
        "physical_effect/pe_radiotherapy_info_landscape.png",
        "Radiotherapy",
        "Radiotherapy"
    ),
    CHEMO(
        "physical_effect/pe_chemo_icon.png",
        "physical_effect/pe_chemo_info_portrait.png",
        "physical_effect/pe_chemo_info_landscape.png",
        "Chemotherapy",
        "Chemotherapy"
    ),
    HER2(
        "physical_effect/pe_her2_icon.png",
        "physical_effect/pe_her2_info_portrait.png",
        "physical_effect/pe_her2_info_landscape.png",
        "HER2+ Therapy",
        "HER2+ Therapy"
    ),
    HORMONAL_TAMOXIFEN(
        "physical_effect/pe_hormonal_icon.png",
        "physical_effect/pe_hormonal_tamoxifen_info_portrait.png",
        "physical_effect/pe_hormonal_tamoxifen_info_landscape.png",
        "Hormonal Therapy",
        "Tamoxifen"
    ),
    HORMONAL_ANASTROZOLE(
        "physical_effect/pe_hormonal_icon.png",
        "physical_effect/pe_hormonal_anastrozole_info_portrait.png",
        "physical_effect/pe_hormonal_anastrozole_info_landscape.png",
        "Hormonal Therapy",
        "Anastrozole\nExemestane\nLetrozole"
    )
}

enum class TherapyMedicineEffect(
    val icon: String, val therapyName: String, val medicine: List<Pair<String, PhysicalEffectInfo>>
) {
    HER2(
        "physical_effect/pe_her2_icon.png", "HER2+ THERAPY", listOf(
            Pair("Trastuzumab (Herceptin®)", PhysicalEffectInfo.HER2),
            Pair("Trastuzumab emtansine (Kadcyla®)", PhysicalEffectInfo.HER2),
            Pair("Pertuzumab (Perjeta®)", PhysicalEffectInfo.HER2)
        )
    ),
    HORMONAL(
        "physical_effect/pe_hormonal_icon.png", "HORMONAL THERAPY",
        listOf(
            Pair("Tamoxifen", PhysicalEffectInfo.HORMONAL_TAMOXIFEN),
            Pair("Anastrozole, Exemestane, Letrozole", PhysicalEffectInfo.HORMONAL_ANASTROZOLE)
        )
    )
}