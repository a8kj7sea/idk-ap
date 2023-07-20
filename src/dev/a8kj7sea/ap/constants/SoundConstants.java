/*
 MIT License

Copyright (c) 2023 a8kj7sea

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */







package dev.a8kj7sea.ap.constants;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundConstants {

    public static enum SoundType {
        SUCCESSFULLY(Sound.LEVEL_UP, "success"),
        INCORRECT_USAGE(Sound.ITEM_BREAK, "incorrect usage"),
        FIX_NEEDED(Sound.BAT_HURT, "fix needed"),
        FIXED(Sound.ANVIL_USE, "fixed"),
        FAILED(Sound.ITEM_BREAK, "failed");

        private Sound sound;
        private String name;

        SoundType(Sound sound, String name) {
            this.sound = sound;
            this.name = name;
        }

        public Sound getSound() {
            return sound;
        }

        public String getName() {
            return name;
        }
    }

    public static Sound getSound(String soundName) {
        Sound sound = null;
        Map<String, SoundType> soundsMap = new HashMap<>();
        soundsMap.put("success", SoundType.SUCCESSFULLY);
        soundsMap.put("incorrect usage", SoundType.INCORRECT_USAGE);
        soundsMap.put("fix needed", SoundType.FIX_NEEDED);
        soundsMap.put("fixed", SoundType.FIXED);
        soundsMap.put("failed", SoundType.FAILED);
        if (soundsMap.containsKey(soundName)) {
            sound = soundsMap.get(soundName).getSound();
            return sound;
        } else {
            return sound;
        }
    }

    public static void playSound(String soundName, Player player) {
        player.playSound(player.getEyeLocation(), getSound(soundName), 0.6f, 6.0f);
    }

}
