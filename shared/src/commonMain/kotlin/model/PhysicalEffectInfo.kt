package model

enum class PhysicalEffectInfo(val icon: String, val info: String, val description: String) {
    SURGERY(
        "physical_effect/pe_surgery_icon.png",
        "physical_effect/pe_surgery_info.png",
        "Surgery"
    ),
    RADIOTHERAPY(
        "physical_effect/pe_radiotherapy_icon.png",
        "physical_effect/pe_radiotherapy_info.png",
        "Radiotherapy"
    ),
    CHEMO(
        "physical_effect/pe_chemo_icon.png",
        "physical_effect/pe_chemo_info.png",
        "Chemotherapy"
    ),
    HER2(
        "physical_effect/pe_her2_icon.png",
        "physical_effect/pe_her2_info.png",
        "HER2+ Therapy"
    ),
    HORMONAL_TAMOXIFEN(
        "physical_effect/pe_hormonal_icon.png",
        "physical_effect/pe_hormonal_tamoxifen_info.png",
        "Hormonal Therapy"
    ),
    HORMONAL_ANASTROZOLE(
        "physical_effect/pe_hormonal_icon.png",
        "physical_effect/pe_hormonal_anastrozole_info.png",
        "Hormonal Therapy"
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