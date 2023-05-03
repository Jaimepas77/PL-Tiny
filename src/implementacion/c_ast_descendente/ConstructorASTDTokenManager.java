/* ConstructorASTDTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. ConstructorASTDTokenManager.java */
package implementacion.c_ast_descendente;
import implementacion.abstractSintax.SintaxisAbstracta;
import implementacion.abstractSintax.SintaxisAbstracta.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class ConstructorASTDTokenManager implements ConstructorASTDConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x600L) != 0L)
            return 104;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 37:
         return jjStopAtPos(0, 13);
      case 40:
         return jjStopAtPos(0, 20);
      case 41:
         return jjStopAtPos(0, 21);
      case 42:
         return jjStopAtPos(0, 11);
      case 43:
         return jjStartNfaWithStates_0(0, 9, 104);
      case 44:
         return jjStopAtPos(0, 28);
      case 45:
         return jjStartNfaWithStates_0(0, 10, 104);
      case 46:
         return jjStopAtPos(0, 26);
      case 47:
         return jjStopAtPos(0, 12);
      case 58:
         return jjStopAtPos(0, 29);
      case 59:
         return jjStopAtPos(0, 22);
      case 60:
         jjmatchedKind = 14;
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 61:
         jjmatchedKind = 23;
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 62:
         jjmatchedKind = 15;
         return jjMoveStringLiteralDfa1_0(0x20000L);
      case 91:
         return jjStopAtPos(0, 24);
      case 93:
         return jjStopAtPos(0, 25);
      case 94:
         return jjStopAtPos(0, 27);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 61:
         if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(1, 16);
         else if ((active0 & 0x20000L) != 0L)
            return jjStopAtPos(1, 17);
         else if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(1, 18);
         else if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(1, 19);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 127;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 104:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  else if (curChar == 48)
                     { jjCheckNAddTwoStates(108, 113); }
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 60)
                        kind = 60;
                     { jjCheckNAdd(106); }
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 60)
                        kind = 60;
                  }
                  break;
               case 0:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 60)
                        kind = 60;
                     { jjCheckNAddStates(3, 6); }
                  }
                  else if ((0x100002700L & l) != 0L)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  else if ((0x280000000000L & l) != 0L)
                     { jjAddStates(7, 10); }
                  else if (curChar == 48)
                  {
                     if (kind > 60)
                        kind = 60;
                     { jjCheckNAddTwoStates(108, 113); }
                  }
                  else if (curChar == 39)
                     { jjCheckNAddTwoStates(18, 19); }
                  break;
               case 2:
                  if ((0xffffffffffffdbffL & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 17:
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(18, 19); }
                  break;
               case 18:
                  if ((0xffffff7fffffdaffL & l) != 0L)
                     { jjCheckNAddTwoStates(18, 19); }
                  break;
               case 19:
                  if (curChar == 39 && kind > 62)
                     kind = 62;
                  break;
               case 103:
                  if ((0x280000000000L & l) != 0L)
                     { jjAddStates(7, 10); }
                  break;
               case 105:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  { jjCheckNAdd(106); }
                  break;
               case 106:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  { jjCheckNAdd(106); }
                  break;
               case 107:
                  if (curChar == 48)
                     { jjCheckNAddTwoStates(108, 113); }
                  break;
               case 109:
                  if ((0x280000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(110, 111); }
                  break;
               case 110:
                  if (curChar == 48 && kind > 61)
                     kind = 61;
                  break;
               case 111:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  { jjCheckNAdd(112); }
                  break;
               case 112:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  { jjCheckNAdd(112); }
                  break;
               case 113:
                  if (curChar == 46)
                     { jjCheckNAddStates(11, 16); }
                  break;
               case 114:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(114, 115); }
                  break;
               case 115:
                  if ((0x3fe000000000000L & l) != 0L && kind > 61)
                     kind = 61;
                  break;
               case 116:
                  if (curChar == 48)
                     { jjCheckNAdd(117); }
                  break;
               case 118:
                  if ((0x280000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(110, 119); }
                  break;
               case 119:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  { jjCheckNAdd(120); }
                  break;
               case 120:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  { jjCheckNAdd(120); }
                  break;
               case 121:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(121, 122); }
                  break;
               case 122:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAdd(117); }
                  break;
               case 123:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 124:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 125:
                  if (curChar != 48)
                     break;
                  if (kind > 60)
                     kind = 60;
                  { jjCheckNAddTwoStates(108, 113); }
                  break;
               case 126:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  { jjCheckNAddStates(3, 6); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 59)
                        kind = 59;
                     { jjCheckNAdd(16); }
                  }
                  else if (curChar == 64)
                  {
                     if (kind > 8)
                        kind = 8;
                     { jjCheckNAdd(2); }
                  }
                  if ((0x1000000010L & l) != 0L)
                     { jjAddStates(17, 18); }
                  else if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(19, 20); }
                  else if ((0x2000000020L & l) != 0L)
                     { jjAddStates(21, 22); }
                  else if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(23, 25); }
                  else if ((0x400000004000L & l) != 0L)
                     { jjAddStates(26, 29); }
                  else if ((0x800000008000L & l) != 0L)
                     { jjAddStates(30, 31); }
                  else if ((0x200000002L & l) != 0L)
                     { jjAddStates(32, 33); }
                  else if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(34, 35); }
                  else if ((0x400000004L & l) != 0L)
                     { jjAddStates(36, 37); }
                  else if ((0x4000000040000L & l) != 0L)
                     { jjAddStates(38, 40); }
                  else if ((0x20000000200L & l) != 0L)
                     { jjAddStates(41, 42); }
                  else if ((0x40000000400000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 13;
                  else if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  else if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 1:
                  if (curChar != 64)
                     break;
                  if (kind > 8)
                     kind = 8;
                  { jjCheckNAdd(2); }
                  break;
               case 2:
                  if (kind > 8)
                     kind = 8;
                  { jjCheckNAdd(2); }
                  break;
               case 3:
                  if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 4:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 5:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 6:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 7:
                  if ((0x2000000020L & l) != 0L && kind > 39)
                     kind = 39;
                  break;
               case 8:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 9:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 10:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 11:
                  if ((0x800000008L & l) != 0L && kind > 40)
                     kind = 40;
                  break;
               case 12:
                  if ((0x40000000400000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 13:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 14:
                  if ((0x4000000040000L & l) != 0L && kind > 57)
                     kind = 57;
                  break;
               case 15:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  { jjCheckNAdd(16); }
                  break;
               case 16:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  { jjCheckNAdd(16); }
                  break;
               case 18:
                  { jjAddStates(43, 44); }
                  break;
               case 20:
                  if ((0x20000000200L & l) != 0L)
                     { jjAddStates(41, 42); }
                  break;
               case 21:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 22:
                  if ((0x10000000100000L & l) != 0L && kind > 30)
                     kind = 30;
                  break;
               case 23:
                  if ((0x4000000040L & l) != 0L && kind > 41)
                     kind = 41;
                  break;
               case 24:
                  if ((0x4000000040000L & l) != 0L)
                     { jjAddStates(38, 40); }
                  break;
               case 25:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 26:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 27:
                  if ((0x100000001000L & l) != 0L && kind > 31)
                     kind = 31;
                  break;
               case 28:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 29:
                  if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 30:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 31:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 32:
                  if ((0x1000000010L & l) != 0L && kind > 49)
                     kind = 49;
                  break;
               case 33:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 34:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 35:
                  if ((0x1000000010L & l) != 0L && kind > 54)
                     kind = 54;
                  break;
               case 36:
                  if ((0x400000004L & l) != 0L)
                     { jjAddStates(36, 37); }
                  break;
               case 37:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 38:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 39;
                  break;
               case 39:
                  if ((0x100000001000L & l) != 0L && kind > 32)
                     kind = 32;
                  break;
               case 40:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 41:
                  if ((0x8000000080L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 42:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 43:
                  if ((0x400000004000L & l) != 0L && kind > 47)
                     kind = 47;
                  break;
               case 44:
                  if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(34, 35); }
                  break;
               case 45:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 46:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 47:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 48:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 49;
                  break;
               case 49:
                  if ((0x8000000080L & l) != 0L && kind > 33)
                     kind = 33;
                  break;
               case 50:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 51:
                  if ((0x2000000020000L & l) != 0L && kind > 46)
                     kind = 46;
                  break;
               case 52:
                  if ((0x200000002L & l) != 0L)
                     { jjAddStates(32, 33); }
                  break;
               case 53:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 54:
                  if ((0x1000000010L & l) != 0L && kind > 34)
                     kind = 34;
                  break;
               case 55:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 56:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 57;
                  break;
               case 57:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 58:
                  if ((0x200000002000000L & l) != 0L && kind > 50)
                     kind = 50;
                  break;
               case 59:
                  if ((0x800000008000L & l) != 0L)
                     { jjAddStates(30, 31); }
                  break;
               case 60:
                  if ((0x4000000040000L & l) != 0L && kind > 35)
                     kind = 35;
                  break;
               case 61:
                  if ((0x4000000040L & l) != 0L && kind > 51)
                     kind = 51;
                  break;
               case 62:
                  if ((0x400000004000L & l) != 0L)
                     { jjAddStates(26, 29); }
                  break;
               case 63:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 64:
                  if ((0x10000000100000L & l) != 0L && kind > 36)
                     kind = 36;
                  break;
               case 65:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 66;
                  break;
               case 66:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 67;
                  break;
               case 67:
                  if ((0x100000001000L & l) != 0L && kind > 37)
                     kind = 37;
                  break;
               case 68:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 69:
                  if ((0x80000000800000L & l) != 0L && kind > 52)
                     kind = 52;
                  break;
               case 70:
                  if ((0x100000001000L & l) != 0L && kind > 56)
                     kind = 56;
                  break;
               case 71:
                  if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(23, 25); }
                  break;
               case 72:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 73;
                  break;
               case 73:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 74;
                  break;
               case 74:
                  if ((0x2000000020L & l) != 0L && kind > 38)
                     kind = 38;
                  break;
               case 75:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 76:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 77;
                  break;
               case 77:
                  if ((0x400000004000L & l) != 0L && kind > 42)
                     kind = 42;
                  break;
               case 78:
                  if ((0x200000002000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 79;
                  break;
               case 79:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 80;
                  break;
               case 80:
                  if ((0x2000000020L & l) != 0L && kind > 58)
                     kind = 58;
                  break;
               case 81:
                  if ((0x2000000020L & l) != 0L)
                     { jjAddStates(21, 22); }
                  break;
               case 82:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 83:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 84:
                  if ((0x2000000020L & l) != 0L && kind > 43)
                     kind = 43;
                  break;
               case 85:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 86;
                  break;
               case 86:
                  if ((0x1000000010L & l) != 0L && kind > 48)
                     kind = 48;
                  break;
               case 87:
                  if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(19, 20); }
                  break;
               case 88:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 89:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 90;
                  break;
               case 90:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 91:
                  if ((0x2000000020L & l) != 0L && kind > 44)
                     kind = 44;
                  break;
               case 92:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 93;
                  break;
               case 93:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 94;
                  break;
               case 94:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 95;
                  break;
               case 95:
                  if ((0x2000000020L & l) != 0L && kind > 55)
                     kind = 55;
                  break;
               case 96:
                  if ((0x1000000010L & l) != 0L)
                     { jjAddStates(17, 18); }
                  break;
               case 97:
                  if ((0x800000008000L & l) != 0L && kind > 45)
                     kind = 45;
                  break;
               case 98:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 99;
                  break;
               case 99:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 100;
                  break;
               case 100:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 101;
                  break;
               case 101:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 102;
                  break;
               case 102:
                  if ((0x2000000020L & l) != 0L && kind > 53)
                     kind = 53;
                  break;
               case 108:
                  if ((0x2000000020L & l) != 0L)
                     { jjCheckNAddStates(45, 47); }
                  break;
               case 117:
                  if ((0x2000000020L & l) != 0L)
                     { jjCheckNAddStates(48, 50); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 18:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(43, 44); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 127 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, "\53", "\55", "\52", 
"\57", "\45", "\74", "\76", "\74\75", "\76\75", "\75\75", "\41\75", "\50", "\51", 
"\73", "\75", "\133", "\135", "\56", "\136", "\54", "\72", null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {
   124, 108, 113, 106, 124, 108, 113, 104, 105, 107, 123, 110, 114, 115, 116, 121, 
   122, 97, 98, 88, 92, 82, 85, 72, 75, 78, 63, 65, 68, 70, 60, 61, 
   53, 55, 45, 50, 37, 40, 25, 28, 33, 21, 23, 18, 19, 109, 110, 111, 
   118, 110, 119, 
};

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public ConstructorASTDTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public ConstructorASTDTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 127; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x7ffffffffffffe01L, 
};
static final long[] jjtoSkip = {
   0x180L, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[127];
    private final int[] jjstateSet = new int[2 * 127];
    private final StringBuilder jjimage = new StringBuilder();
    private StringBuilder image = jjimage;
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;
}