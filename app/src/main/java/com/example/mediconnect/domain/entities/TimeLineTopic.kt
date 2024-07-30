package com.example.mediconnect.domain.entities

data class TimeLineTopic (var isCheckedStep1: Boolean ? = false,
                          var isCheckedStep2: Boolean ? = false,
                          var isCheckedStep3: Boolean ? = false,
                          var step1: Boolean ? = true,
                          var step2: Boolean ? = false,
                          var step3: Boolean ? = false)