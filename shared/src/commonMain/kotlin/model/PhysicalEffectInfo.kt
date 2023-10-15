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
    CHEMO("physical_effect/pe_chemo_icon.png",
        "physical_effect/pe_chemo_info.png",
        "Chemotherapy"
    ),
    HER2(
        "physical_effect/pe_her2_icon.png",
        "physical_effect/pe_her2_info.png",
        "HER2+ Therapy"
    ),
}