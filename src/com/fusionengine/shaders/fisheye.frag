void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;
    float aspectRatio = iResolution.x / iResolution.y;
    float strength = sin(iGlobalTime * 2.0) * 0.02;

    vec2 intensity = vec2(strength * aspectRatio,
                          strength * aspectRatio);

    vec2 coords = uv;
    coords = (coords - 0.5) * 2.0;

    vec2 realCoordOffs;
    realCoordOffs.x = (1.0 - coords.y * coords.y) * intensity.y * (coords.x);
    realCoordOffs.y = (1.0 - coords.x * coords.x) * intensity.x * (coords.y);

    vec4 color = texture2D(iChannel0, uv - realCoordOffs);

	fragColor = vec4(color);
}