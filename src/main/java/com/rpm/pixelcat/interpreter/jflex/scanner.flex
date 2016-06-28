package com.rpm.pixelcat.interpreter;

/**
* Scanner provides a scanner for the {@link Parser} grammar.
*/

import java_cup.runtime.*;

%%

%class Scanner
%unicode
%cup
%line
%column

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%eofval{
  return symbol(Tokens.EOF);
%eofval}

%%


"+"             { return symbol(Tokens.PLUS); }
"-"             { return symbol(Tokens.MINUS); }
"*"             { return symbol(Tokens.MUL); }
"/"             { return symbol(Tokens.DIV); }
"("             { return symbol(Tokens.LPAREN); }
")"             { return symbol(Tokens.RPAREN); }
[0-9]+\.[0-9]+  { return symbol(Tokens.FLOAT, yytext()); }
[0-9]+          { return symbol(Tokens.INT, yytext()); }
[ \t\r\n\f]     { /* ignore white space */ }
.               { ParserUtil.getPrinter().printError(new Exception("Illegal character: "+yytext())); }