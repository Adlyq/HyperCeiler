/*
  * This file is part of HyperCeiler.

  * HyperCeiler is free software: you can redistribute it and/or modify
  * it under the terms of the GNU Affero General Public License as
  * published by the Free Software Foundation, either version 3 of the
  * License.

  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU Affero General Public License for more details.

  * You should have received a copy of the GNU Affero General Public License
  * along with this program.  If not, see <https://www.gnu.org/licenses/>.

  * Copyright (C) 2023-2024 HyperCeiler Contributions
*/
package com.sevtinge.hyperceiler.module.hook.home.title

import android.text.*
import android.widget.*
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createAfterHook
import com.github.kyuubiran.ezxhelper.finders.*
import com.sevtinge.hyperceiler.module.hook.home.*
import com.sevtinge.hyperceiler.utils.*
import de.robv.android.xposed.*

class TitleMarquee : HomeBaseHook() {

    override fun initForNewHome() {
        MethodFinder.fromClass("com.miui.home.launcher.ShortcutIcon").filterByName("onFinishInflate").first()
            .createAfterHook {
                with(it.thisObject as TextView) {
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    marqueeRepeatLimit = -1
                    isSelected = true
                    isSingleLine = true
                    // isHorizontalFadingEdgeEnabled = true
                    // setHorizontallyScrolling(true)
                }
            }
        MethodFinder.fromClass("com.miui.home.launcher.ShortcutIcon").filterByName("isNeedDrawIconMask").first().replaceMethod { false }
    }

    override fun initForHomeLower9777() {
        MethodFinder.fromClass("com.miui.home.launcher.ItemIcon").filterByName("onFinishInflate").first()
            .createAfterHook {
                val mTitle: TextView = try {
                    XposedHelpers.getObjectField(it.thisObject, "mTitleView") as TextView
                } catch (t: Throwable) {
                    XposedHelpers.getObjectField(it.thisObject, "mTitle") as TextView
                }
                with(mTitle) {
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    isHorizontalFadingEdgeEnabled = true
                    isSingleLine = true
                    marqueeRepeatLimit = -1
                    isSelected = true
                    setHorizontallyScrolling(true)
                }
            }
    }
}

