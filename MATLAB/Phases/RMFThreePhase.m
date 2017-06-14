t = 0:0.0001:1/60*5;
V_r = 10*sin(100*pi*t);
V_y = 10*sin(100*pi*t-2*pi/3);
V_b = 10*sin(100*pi*t-4*pi/3);
% plot(t,V_r,t,V_y,t,V_b)
V_Ver = V_r - V_y*cos(pi/3) -  V_b*cos(pi/3);
V_Hor = - V_y*cos(pi/6) +  V_b*cos(pi/6);
V_mag = sqrt(V_Ver.^2 + V_Hor.^2);
V_ang = atan(V_Ver./V_Hor);
plot(t,V_mag)
plot(t,V_ang)