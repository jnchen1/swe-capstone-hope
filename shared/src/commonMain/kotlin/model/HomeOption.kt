package model

import androidx.compose.ui.graphics.Color

enum class HomeOptions(val title: String, val text: String, val color: Color) {
    WHAT_IS_SURVIVORSHIP("SURVIVORSHIP","1. What is cancer survivorship?", Color(0xFFFF7E79)),
    PHYSICAL_EFFECT("PHYSICAL EFFECT","2. Physical effects", Color(0xFFF4B183)),
    EMOTIONAL_EFFECT("EMOTIONAL EFFECT","3. Emotional effects", Color(0xFFFFE699)),
    FOLLOWUP_CARE("FOLLOWUP CARE","4. Follow-up care options", Color(0xFFC6E0B5)),
    COMPARE_CARE("COMPARE THE CARE","5. Comparing options", Color(0xFFA0E6E3)),
    WHAT_MATTERS("PREFERENCE","6. Finding out what matters to you", Color(0xFF9DC3E6)),
    CONCLUSION("CONCLUSION","7. Conclusion", Color(0xFFBCBBE7)),
    RESOURCES("RESOURCES","Other online resources", Color(0xFFEBACEF))
}