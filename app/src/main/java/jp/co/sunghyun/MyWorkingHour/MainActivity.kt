package jp.co.sunghyun.MyWorkingHour

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var startHour: String
        var startMinute: String
        var endHour: String
        var endMinute: String
        var workingHour: String
        var workingMinute: String
        var restMinute: String

        BtnCal.setOnClickListener {
            startHour = StartHour.text.toString()
            startMinute = StartMinute.text.toString()
            endHour = EndHour.text.toString()
            endMinute = EndMinute.text.toString()
            restMinute = RestMinute.text.toString()

            if (startHour.isNotBlank() && startMinute.isNotBlank()
                && endHour.isNotBlank() && endMinute.isNotBlank()
            ) {

                if (endMinute.toInt() < startMinute.toInt()) {
                    endMinute = (endMinute.toInt() + 60).toString()
                    endHour = (endHour.toInt() - 1).toString()
                }
                // 休憩時間を省いた勤務時間
                workingHour = (endHour.toInt() - startHour.toInt()).toString()
                workingMinute = (endMinute.toInt() - startMinute.toInt()).toString()

                // 休み時間がない場合０にセット
                if (restMinute.isBlank()) {
                    restMinute = "0"
                }

                // 休憩時間時間が1時間超えた場合
                if (restMinute.toInt() !in 0..60) {
                    // 休憩時間の分を時に換えて計算する。
                    var restHour: Int
                    restHour = restMinute.toInt() / 60
                    restMinute = (restMinute.toInt() % 60).toString()

                    // 勤務時間の分より休憩時間の分が大きい場合
                    if (workingMinute.toInt() < restMinute.toInt()) {
                        workingMinute = (workingMinute.toInt() + 60).toString()
                        workingHour = (workingHour.toInt() - 1).toString()
                    }
                    workingHour = (workingHour.toInt() - restHour.toInt()).toString()
                    workingMinute = (workingMinute.toInt() - restMinute.toInt()).toString()

                    // 結果をセットする。
                    WorkingHour.text = workingHour
                    WorkingMinute.text = workingMinute
                } else { // 休憩時間が1時間以下の場合
                    // 勤務時間の分より休憩時間の分が大きい場合
                    if (workingMinute.toInt() < restMinute.toInt()) {
                        workingMinute = (workingMinute.toInt() + 60).toString()
                        workingHour = (workingHour.toInt() - 1).toString()
                    }
                    workingMinute = (workingMinute.toInt() - restMinute.toInt()).toString()

                    // 結果をセットする。
                    WorkingHour.text = workingHour
                    WorkingMinute.text = workingMinute
                }
            } else {
                Toast.makeText(this, getString(R.string.pleaseWorkingTime), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
