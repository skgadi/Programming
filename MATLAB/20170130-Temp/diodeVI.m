clear
m = units;
V_d = -1:0.001:1; % voltage across diode
I_s = 1e-6; % reverse saturation current
T = 300; % p-n junction temperature
n=1; % ideality factor
V_t = m.k*T/(m.e); % thermal voltage
I = I_s.*(exp(V_d./(V_t*n))-1); % diode equation
plot (V_d, I)