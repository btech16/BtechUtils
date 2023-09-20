package com.rhdev.btechutils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material3.Mdc3Theme

@Composable
fun RahbaCompose() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 26.dp)
    )
    {
        Image(
            painterResource(id = R.drawable.rahba_developer_logos_blue_large),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .heightIn(max = 160.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            SocialIcon(
                icon = painterResource(id = R.drawable.site_svgrepo_com),
                url = "https://rahbadev.github.io/",
                color = Color(0xFF3b3537)
            )

            SocialIcon(
                icon = painterResource(id = R.drawable.twitter_154_svgrepo_com),
                url = "https://twitter.com/rahbadev",
                color = Color(0xFF1DA1F2)
            )

            SocialIcon(
                icon = painterResource(id = R.drawable.google_play_store_svgrepo_com),
                url = StoreUtils.GOOGLE_PLAY_DEV_URL,
                color = Color(0xFFb6547c)
            )

            SocialIcon(
                icon = painterResource(id = R.drawable.facebook_176_svgrepo_com),
                url = "https://www.facebook.com/rahbadev/",
                color = Color(0xFF4267B2)            )

            SocialIcon(
                icon = painterResource(id = R.drawable.github_svgrepo_com),
                url = "https://github.com/rahbadev",
                color = Color(0xFF010101)
            )



        }
    }

}

@Composable
fun SocialIcon(icon: Painter, url: String = "" , color: Color) {
    val context = LocalContext.current
    IconButton(
        onClick = {
                  openUrlByBrowser(
                      context = context ,
                      url = url
                  )
        },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(24.dp)
        ,
        colors = IconButtonDefaults.iconButtonColors(containerColor = color , contentColor = Color.White,)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(4.dp)
        )
    }
}

private fun openUrlByBrowser(context: Context, url:String) {
    context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
    )
}
@Preview(locale = "ar", showBackground = true, showSystemUi = true, device = "id:pixel_6a")
@Composable
fun RahbaComposePreview() {
    Mdc3Theme {
        RahbaCompose()
    }
}