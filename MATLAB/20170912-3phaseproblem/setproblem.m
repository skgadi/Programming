clc
clear
a = exp(1i*pi*2/3);

% 
% 
% I_1 = 10;
% I_2 = 3*exp(-1i*pi*2/3);
% I_3 = 6*exp(-1i*pi*4/3);
% I_a = I_1-I_3;
% I_b = I_2-I_1;
% I_c = I_3-I_2;
% 
% % I_p = [10; 3*a^2; 6*a];
% I = [
%     I_a;
%     I_b;
%     I_c;
%     ];
% ToSC = (1/3)*[
%     1,  1,      1;
%     1,  a,    a^2;
%     1,  a^2,    a;
%     ];
% I_SC = ToSC*I;
% 
% V= [311; 311*a^2; 311*a];
% V_SC = ToSC*V;
% 
% 
% I_1 = 10;
% I_2 = 3*exp(-1i*pi*2/3);
% I_3 = 6*exp(-1i*pi*4/3);
% I_a = I_1-I_3;
% I_b = I_2-I_1;
% I_c = I_3-I_2;
% 
% V_1 = 311;
% V_2 = 311*exp(-1i*pi*2/3);
% V_3 = 311*exp(-1i*pi*4/3);
% 
% A = [
%     I_a,	-I_b,	0;
%     0,      I_b,	-I_c;
%     -I_a,	0,      I_c
% ];
% B = [V_1; V_2; V_3];
% 
% R = A\B;
% 
% 


V = 311.127*[
    1;
    a;
    a^2;
    ];
I_n = [
    10;
    6*a;
    ];
I_n(3) = -I_n(1)-I_n(2);
I = [
    1,  0,  -1;
    -1,	1,  0;
    0,  -1, 1;
    ]*I_n;
X = 1i*2*pi*60*1e-3*[
    1;
    1;
    1;
    ];
IZ = I_n.*X;
V_c= V-IZ;
Z_3 = 10;
Z_1 = (V_c(3)-I(3)*Z_3)/(-I(1));
Z_2 = (V_c(2)+I(3)*Z_3)/I(2);


abs(I_n)./sqrt(2)
abs(I)./sqrt(2)
fprintf('Branch 1: R = %f; X_L = %fm\n',real(Z_1), 1e3*imag(Z_1)/(2*pi*60));
fprintf('Branch 2: R = %f; X_C = %fu\n',real(Z_2), -1e6/(2*pi*60*imag(Z_2)));
fprintf('Branch 3: R = %f; X_L = %f\n',real(Z_3), imag(Z_3)/(2*pi*60));
