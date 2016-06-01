package com.brownrice.matchtouch;

public class StageData
{
    
    public static final int         RECT = 2;
    
    public static int Stages[][] =
    {
        /*  index 0 - changeable image count in using 'random().nextInt(int)' function
         *  index 1 - start image index : add to start image index in random function(it means opset)
         *  index 2 - end image index(not used)
         *  index 3 - duration of question image display 
         *  index 4 - image category
         */
       
        // stage for test
        // {3, 0, 0, 3000, TILES.TILE_NUM_RAINBOW},
        
		// {region of tiles}, {index of start}, {NULL}, {question tile display duration}, {tile category}
        /*****************************************************************************************/
        
        // stage 1 ~ 40 : numeric, image index 00 ~ 20
        { 3, 0, 0, 3000, TILES.TILE_NUM_RAINBOW},   // 01
        { 3, 1, 0, 3000, TILES.TILE_NUM_RAINBOW},   // 02
        { 3, 2, 0, 3000, TILES.TILE_NUM_RAINBOW},   // 03
        { 4, 1, 0, 3000, TILES.TILE_NUM_RAINBOW},   // 04
        { 4, 2, 0, 3000, TILES.TILE_NUM_RAINBOW},   // 05
        { 4,13, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 06
        { 4, 4, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 07
        { 4, 4, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 08
        { 4,14, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 09
        { 5,15, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 10
        { 5, 4, 0, 2500, TILES.TILE_NUM_RAINBOW},   // 11
        { 5, 5, 0, 2200, TILES.TILE_NUM_RAINBOW},   // 12
        { 5,15, 0, 2200, TILES.TILE_NUM_RAINBOW},   // 13
        { 5, 5, 0, 2200, TILES.TILE_NUM_RAINBOW},   // 14
        { 5,11, 0, 2200, TILES.TILE_NUM_RAINBOW},   // 15
        { 5, 6, 0, 2200, TILES.TILE_NUM_RAINBOW},   // 16
        { 5, 6, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 17
        { 5,16, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 18
        { 5, 7, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 19
        { 6,12, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 20
        { 6, 7, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 21
        { 6, 8, 0, 2000, TILES.TILE_NUM_RAINBOW},   // 22
        { 6, 6, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 23
        { 6, 9, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 24
        { 6, 9, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 25
        { 6,10, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 26
        { 6, 1, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 27
        { 6,13, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 28
        { 6,10, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 29
        { 6,11, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 30
        { 6,12, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 31
        { 6,14, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 32
        { 6, 3, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 33
        { 6, 6, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 34
        { 6, 9, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 35
        { 6,12, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 36
        { 7, 0, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 37
        { 7,11, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 38
        { 7,13, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 39
        { 7,14, 0, 1800, TILES.TILE_NUM_RAINBOW},   // 40
        
        //-----------------------------------------------------------------------------------------
        // stage 41 ~ 80 : animal, image index 00 ~ 21
        { 3, 0, 0, 3000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 41 
        { 3, 1, 0, 3000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 42
        { 3, 2, 0, 3000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 43
        { 4, 1, 0, 3000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 44
        { 4, 2, 0, 3000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 45
        { 4,13, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 46
        { 4, 4, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 47
        { 4, 4, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 48
        { 4,14, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 49
        { 5,15, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 50
        { 5, 4, 0, 2500, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 51
        { 5, 5, 0, 2200, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 52
        { 5,15, 0, 2200, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 53
        { 5, 5, 0, 2200, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 54
        { 5,11, 0, 2200, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 55
        { 5, 6, 0, 2200, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 56
        { 5, 6, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 57
        { 5,16, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 58
        { 5, 7, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 59
        { 6,12, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 60
        { 6, 7, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 61
        { 6, 8, 0, 2000, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 62
        { 6, 6, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 63
        { 6, 9, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 64
        { 6, 9, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 65
        { 6,10, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 66
        { 6, 1, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 67
        { 6,13, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 68
        { 6,10, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 69
        { 6,11, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 70
        { 6,12, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 71
        { 6,14, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 72
        { 6, 3, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 73
        { 6, 6, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 74
        { 6, 9, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 75
        { 6,12, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 76
        { 7, 0, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 77
        { 7,11, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 78
        { 7,13, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 79
        { 7,14, 0, 1800, TILES.TILE_AINMAL_SILVER_RAINBOW},  // 80
        
        
        // stage 81 ~ 120 : transport, image index 00 ~ 21
        { 3, 0, 0, 3000, TILES.TILE_TRANSPORT_RAINBOW},  // 81
        { 3, 1, 0, 3000, TILES.TILE_TRANSPORT_RAINBOW},  // 82
        { 3, 2, 0, 3000, TILES.TILE_TRANSPORT_RAINBOW},  // 83
        { 4, 1, 0, 3000, TILES.TILE_TRANSPORT_RAINBOW},  // 84
        { 4, 2, 0, 3000, TILES.TILE_TRANSPORT_RAINBOW},  // 85
        { 4,13, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 86
        { 4, 4, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 87
        { 4, 4, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 88
        { 4,14, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 89
        { 5,15, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 90
        { 5, 4, 0, 2500, TILES.TILE_TRANSPORT_RAINBOW},  // 91
        { 5, 5, 0, 2200, TILES.TILE_TRANSPORT_RAINBOW},  // 92
        { 5,15, 0, 2200, TILES.TILE_TRANSPORT_RAINBOW},  // 93
        { 5, 5, 0, 2200, TILES.TILE_TRANSPORT_RAINBOW},  // 94
        { 5,11, 0, 2200, TILES.TILE_TRANSPORT_RAINBOW},  // 95
        { 5, 6, 0, 2200, TILES.TILE_TRANSPORT_RAINBOW},  // 96
        { 5, 6, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 97
        { 5,16, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 98
        { 5, 7, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 99
        { 6,12, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 100
        { 6, 7, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 101
        { 6, 8, 0, 2000, TILES.TILE_TRANSPORT_RAINBOW},  // 102
        { 6, 6, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 103
        { 6, 9, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 104
        { 6, 9, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 105
        { 6,10, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 106
        { 6, 1, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 107
        { 6,13, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 108
        { 6,10, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 109
        { 6,11, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 110
        { 6,12, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 111
        { 6,14, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 112
        { 6, 3, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 113
        { 6, 6, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 114
        { 6, 9, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 115
        { 6,12, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 116
        { 7, 0, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 117
        { 7,11, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 118
        { 7,13, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 119
        { 7,14, 0, 1800, TILES.TILE_TRANSPORT_RAINBOW},  // 120
        // { 7,14, 0, 4000, TILES.TILE_TRANSPORT_RAINBOW},  // 120 -- last stage test data
        

        // stage 121 ~ 160 : human face, image index 00 ~ 21
        { 3, 0, 0, 3000, TILES.TILE_FACE_RAINBOW},  // 121 
        { 3, 1, 0, 3000, TILES.TILE_FACE_RAINBOW},  // 122 
        { 3, 2, 0, 3000, TILES.TILE_FACE_RAINBOW},  // 123 
        { 4, 1, 0, 3000, TILES.TILE_FACE_RAINBOW},  // 124 
        { 4, 2, 0, 3000, TILES.TILE_FACE_RAINBOW},  // 125
        { 4,13, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 126
        { 4, 4, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 127
        { 4, 4, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 128
        { 4,14, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 129
        { 5,15, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 130
        { 5, 4, 0, 2500, TILES.TILE_FACE_RAINBOW},  // 131
        { 5, 5, 0, 2200, TILES.TILE_FACE_RAINBOW},  // 132
        { 5,15, 0, 2200, TILES.TILE_FACE_RAINBOW},  // 133
        { 5, 5, 0, 2200, TILES.TILE_FACE_RAINBOW},  // 134
        { 5,11, 0, 2200, TILES.TILE_FACE_RAINBOW},  // 135
        { 5, 6, 0, 2200, TILES.TILE_FACE_RAINBOW},  // 136
        { 5, 6, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 137
        { 5,16, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 138
        { 5, 7, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 139
        { 6,12, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 140
        { 6, 7, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 141
        { 6, 8, 0, 2000, TILES.TILE_FACE_RAINBOW},  // 142
        { 6, 6, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 143
        { 6, 9, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 144
        { 6, 9, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 145
        { 6,10, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 146
        { 6, 1, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 147
        { 6,13, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 148
        { 6,10, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 149
        { 6,11, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 150
        { 6,12, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 151
        { 6,14, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 152
        { 6, 3, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 153
        { 6, 6, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 154
        { 6, 9, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 155
        { 6,12, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 156
        { 7, 0, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 157
        { 7,11, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 158
        { 7,13, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 159
        { 7,14, 0, 1800, TILES.TILE_FACE_RAINBOW},  // 160
        /*****************************************************************************************/
    };
}