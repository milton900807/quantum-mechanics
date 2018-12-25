
class ITrig {
    static float sin(int a) {
        float da = (float)((double)a * 3.141592653589793D / 180.0D);
        return (float)Math.sin((double)da);
    }

    static float cos(int a) {
        float da = (float)((double)a * 3.141592653589793D / 180.0D);
        return (float)Math.cos((double)da);
    }

    ITrig() {
    }
}
