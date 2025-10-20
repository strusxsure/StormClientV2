#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Radius;

in vec2 texCoord;

out vec4 outColor;

void main() {
    vec2 pixelSize = 1.0 / InSize;
    vec4 color = vec4(0.0);
    float totalWeight = 0.0;

    for (float x = -Radius; x <= Radius; x++) {
        for (float y = -Radius; y <= Radius; y++) {
            float weight = exp(-(x * x + y * y) / (2.0 * Radius * Radius));
            color += texture(DiffuseSampler, texCoord + vec2(x, y) * pixelSize) * weight;
            totalWeight += weight;
        }
    }

    outColor = color / totalWeight;
}