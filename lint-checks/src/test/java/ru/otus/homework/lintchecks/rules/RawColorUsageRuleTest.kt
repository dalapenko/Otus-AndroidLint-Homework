package ru.otus.homework.lintchecks.rules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test
import ru.otus.homework.lintchecks.Mock

@Suppress("UnstableApiUsage")
class RawColorUsageRuleTest {

    @Test
    fun detektRawColorInLayoutXml() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.xml(
                    "res/layout/incorrect_color_usages_layout.xml",
                    """
                    <?xml version="1.0" encoding="utf-8"?>
                    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">
                    
                        <View
                            android:id="@+id/case1"
                            android:layout_width="80dp"
                            android:layout_height="80dp"
                            android:layout_marginTop="32dp"
                            android:background="#FF3700B3"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />
                    
                        
                        <View
                            android:id="@+id/case6"
                            android:layout_width="80dp"
                            android:layout_height="80dp"
                            android:layout_marginTop="16dp"
                            android:background="@drawable/ic_baseline_adb_24"
                            android:backgroundTint="@color/purple_200"
                            android:backgroundTintMode="add"
                            app:layout_constraintEnd_toEndOf="@+id/case5"
                            app:layout_constraintStart_toStartOf="@+id/case5"
                            app:layout_constraintTop_toBottomOf="@+id/case5" />
                    
                    </androidx.constraintlayout.widget.ConstraintLayout>
                """.trimIndent()
                ), Mock.colorXml
            )
            .issues(RawColorUsageRule.ISSUE)
            .run()
            .expectErrorCount(1)
    }

    @Test
    fun detektRawColorInColorSelectorXml() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.xml(
                    "res/color/selector.xml",
                    """
                    <?xml version="1.0" encoding="utf-8"?>
                    <selector xmlns:android="http://schemas.android.com/apk/res/android">
                        <item android:color="#5C6BC0" android:state_enabled="true" />
                        <item android:color="#C5CAE9" />
                    </selector>
                """.trimIndent()
                ), Mock.colorXml
            )
            .issues(RawColorUsageRule.ISSUE)
            .run()
            .expectErrorCount(2)
    }

    @Test
    fun detektRawColorInDrawableXml() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                LintDetectorTest.xml(
                    "res/drawable/ic_baseline_adb_24.xml",
                    """
                    <vector xmlns:android="http://schemas.android.com/apk/res/android"
                        android:width="24dp"
                        android:height="24dp"
                        android:tint="#b4ffff"
                        android:tintMode="multiply"
                        android:viewportWidth="24"
                        android:viewportHeight="24">
                        <path
                            android:fillColor="@color/teal_200"
                            android:pathData="M5,16c0,3.87 3.13,7 7,7s7,-3.13 7,-7v-4L5,12v4zM16.12,4.37l2.1,-2.1 -0.82,-0.83 -2.3,2.31C14.16,3.28 13.12,3 12,3s-2.16,0.28 -3.09,0.75L6.6,1.44l-0.82,0.83 2.1,2.1C6.14,5.64 5,7.68 5,10v1h14v-1c0,-2.32 -1.14,-4.36 -2.88,-5.63zM9,9c-0.55,0 -1,-0.45 -1,-1s0.45,-1 1,-1 1,0.45 1,1 -0.45,1 -1,1zM15,9c-0.55,0 -1,-0.45 -1,-1s0.45,-1 1,-1 1,0.45 1,1 -0.45,1 -1,1z" />
                    </vector>
                """.trimIndent()
                ), Mock.colorXml
            )
            .issues(RawColorUsageRule.ISSUE)
            .run()
            .expectErrorCount(1)
    }
}

