/*
 * Copyright 2017 Mirko Sertic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mirkosertic.gameengine.teavmwasm;

import de.mirkosertic.gameengine.core.GameResource;
import de.mirkosertic.gameengine.type.Color;
import de.mirkosertic.gameengine.type.EffectCanvas;
import de.mirkosertic.gameengine.type.Position;

public class WASMEffectCavas implements EffectCanvas {

    @Override
    public void drawSingleDot(String aObjectIdentifier, Position aPosition, Color aColor, int aZIndex) {
    }

    @Override
    public void fillRectangle(String aObjectIdentifier, int aX0, int aY0, int aX1, int aY1, int aX2, int aY2, int aX3,
            int aY3, Color aColor, int aZIndex) {
    }

    @Override
    public void fillRectangle(String aObjectIdentifier, GameResource aTexture, int aX0, int aY0, int aX1, int aY1,
            int aX2, int aY2, int aX3, int aY3, double aU0, double aV0, double aU1, double aV1, double aU2, double aV2,
            double aU3, double aV3, int aZIndex) {
    }
}
