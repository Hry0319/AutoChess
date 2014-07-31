//new start!
#define MAX_MOVE 40
#define MAX_DEEP 10
//其?是9?

#define NONE 0
#define BLACK 1
#define WHITE 2
#define DUMMY 3

int put[8]={-10,-9,-8,-1,1,8,9,10};
int move[MAX_DEEP][MAX_MOVE+1];
char board[MAX_DEEP][90];
int BestMove,BestScore,deep;
int score;

int colour;//CheckWay()&check()
int way;//check()&turn()
int num,deep_S;//MakeMove()&G()
int now;//turn()
int ValueBlack,ValueWhite;//G()

int CheckWay(int deep,int num,int way){
    for(num+=put[way]<<1;
    board[deep][num]&&board[deep][num]!=DUMMY;num+=put[way])
        if(colour==board[deep][num])
            return 1;
    return 0;
}
int check(int deep,int num){
    colour=(deep+1)%2+1;
    for(way=0;way<8;way++)
        if(board[deep][num+put[way]]==3-colour&&CheckWay(deep,num,way))
            return 1;
    return 0;
}
//?想到我的程序?么晦?了
void MakeMove(int deep){
    deep_S=deep-1;
    move[deep][0]=0;
    for(num=10;num<82;num++)
        if(!board[deep_S][num]&&check(deep_S,num))
            move[deep][ ++move[deep][0] ]=num;
}
int turn(int deep,int num){
    colour=deep%2+1;
    for(now=0;now<90;now++)
        board[deep][now]=board[deep-1][now];
    for(way=0;way<8;way++)
        if(board[deep][num+put[way]]==colour&&CheckWay(deep,num,way))
            for(now=num+put[way];board[deep][now]!=colour;now+=put[way])
                board[deep][now]=colour;
    board[deep][num]=colour;
}
int G(deep){
    ValueWhite=0;
    ValueBlack=0;
    for(num=10;num<82;num++)
        if(board[deep][num]==WHITE)
            ValueWhite+=value[num];
        else if(board[deep][num]==BLACK)
            ValueBlack+=value[num];
    if(deep%2)
        return ValueBlack-ValueWhite;
    return ValueWhite-ValueBlack;
}
int alpha_beta(int deep,int alpha,int beta){
    if(deep==MAX_DEEP)
        return G(deep-1);
    MakeMove(deep);
    if(move[deep][0]==0)
        return G(deep-1);
    for(;move[deep][0]>0;move[deep][0]--){
        turn(deep,move[deep][ move[deep][0] ]);
        score=-alpha_beta(deep+1,-beta,-alpha);
        if(score>=alpha){
            alpha=score;
            if(alpha>=beta)
                break;
        }
    }
    return alpha;
}
int search(){
    BestScore=-65535;
    MakeMove(1);
    for(;move[1][0]>0;move[1][0]--){
        turn(1,move[1][ move[1][0] ]);
        score=-alpha_beta(2,-65536,-BestScore);
        if(score>BestScore){
            BestScore=score;
            BestMove=move[1][ move[1][0] ];
        }
    }
    return BestMove;
}//配合以前?的那?……貌似有???……
//我的???格?化好大啊
//我省略了一部分，所以?法?行…………
//?行了??程序,才??速?64 2800+和酷睿2的效率差距好大
//用了 gnome的?表后，智力明?提升……