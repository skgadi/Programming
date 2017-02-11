function [m,c] = units
%UNITS Create structs of MKS and CGS units and constants
% The units.m function returns structs containing the SI (MKS) and
% Gaussian (CGS) values of many common units and physical constants.
% Those unfamiliar with CGS units should note that electromagnetic units
% are defined differently than MKS and thus different equations must be
% used or quantities must be converted to MKS units.
%
% The example below demonstrates how to use 
% this struct to cleanly and effeciently perform matlab calculations 
% using commonly encountered units and physical constants. The code 
% is easily modified to include any non-standard unit or constant desired.
%
% To determine the exact syntax of a particular unit you would like to use,
% simply run the function with no semicolon and all the units will be
% displayed.
%
% Using the units struct:
% --------------------------------------------------------
%  %First create a units struct by including in your code the following
%  %line
%       [m,c] = units;
%  %Then:
% 
%  %To enter a number in a given unit, MULTIPLY by the unit:
%        L = 5*m.in   % matlab automatically displays L in SI units
% 
%  % To display in a desired unit, simply divide by that unit
%        L/m.ft       % displays L in ft.
% 
%  % To convert between units, MULTIPLY by the starting unit, and
%  % DIVIDE by the ending unit:
%        m.mi^2/m.ft^2  %displays the number of feet^2 in one mile^2
% 
%  %More complicated units can be obtained through arithmatic
%        mach1 = 340.29*m.m/m.s;  %speed of sound 
% 
%  %Note... to make the speed of sound available wherever your
%  %units struct is defined, simply write:
%        m.mach1 = 340.29*m.m/m.s;   %mach1 now part of units struct
% 
%  %If only one output variable is defined, only the MKS struct will be
%  %created
%        m = units;
%
% %------  BEGIN EXAMPLE CODE --------------------------------
% %This is an example calculation that uses the units mfile to calculate
% %the pressure at the bottom of a long vertically oriented pipe that is
% %capped at the bottom and filled with oil.
% 
% m = units;
% pipeInnerDiameter = 4*m.in;     %4 inch inner diameter
% pipeHeight = 30*m.ft;           %pipe sticks 30 feet up into the air
% densityOfOil = 0.926*m.gm/m.cc; %density of oil as found on some random web site = .926 gm/cc
% pipeCrossSectionArea = pi*(pipeInnerDiameter/2)^2;  %cross sectional area of pipe bore
% volumeOfOil = pipeCrossSectionArea * pipeHeight;    %volume of oil that the pipe can hold
% pressurePipeBottom = densityOfOil * m.g * pipeHeight;  %pressure formula from physics: P = rho*g*h.
% forceOnPipeBottom = pressurePipeBottom * pipeCrossSectionArea;  %force exterted on bottom cap of the pipe.
% 
% %Note that each variable holds its value as expressed in SI units.  To
% %express these values in different units, simply divide by the desired
% %unit as shown below.
% line1 = sprintf('A %2.3g inch diameter pipe sticking %3.3g meters into the air and filled',pipeInnerDiameter/m.in, pipeHeight/m.m);
% line2 = sprintf('with %3.3g fluid ounces of oil will have a pressure at the bottom of %4.4g psi.',volumeOfOil/m.floz, pressurePipeBottom/m.psi);
% line3 = sprintf('This will cause a total force of %5.5g lbs to press on the bottom cap of the pipe.',forceOnPipeBottom/m.lbf);
% 
% textVal = sprintf('\n\n%s\n%s\n%s\n',line1,line2,line3);
% disp(textVal);
%%------  END EXAMPLE CODE --------------------------------



%============ START THE ACTUAL CODE TO DEFINE THE UNITS STRUCT =========
%-------- MKS UNITS ------------------------------
%---- Fundamental units ----
m.m = 1;
m.kg = 1;
m.s = 1;
m.K = 1;
m.A = 1;
m.mol = 1;

%---- Derived units ----
m.L = 1e-3 * m.m ^3;
m.Hz = 1/m.s;
m.N = m.kg * m.m / m.s^2;
m.J = m.N * m.m;
m.Pa = m.N / m.m^2;
m.W = m.J / m.s;
m.C = m.A / m.s;
m.V = m.J / m.C;
m.Wb = m.V * m.s;
m.T = m.Wb / m.m ^2;
m.F = m.C / m.V;
m.ohm = m.V / m.A;
m.siemens = 1 / m.ohm;
m.H = m.Wb / m.A;

%---- Mechanical CGS units in MKS ----
m.cm = 1e-2 * m.m;
m.gm = 1e-3 * m.kg;
m.dyne = m.gm * m.cm / m.s^2;
m.erg = m.dyne * m.cm;
m.poise = m.gm / m.cm / m.s;

%---- Defined constants ----
m.c = 299792458 * m.m / m.s; %Speed of light
m.g = 9.80665 * m.m/m.s^2; %Standard gravitational acceleration on Earth
m.T0 = 273.15 * m.K; %0 degrees Celsius
m.atm = 101325 * m.Pa; %Standard atmospheric pressure
m.mu0 = 4 * pi * m.erg / m.J * m.H / m.m; %Permeability of free space

%---- Electromagnetic CGS units in MKS ----
m.stA = sqrt(m.cm/m.m) / m.c * m.A;
m.stC =  m.stA * m.s;
m.stV = m.erg / m.stC;
m.Mx = m.stV * m.s / (m.c * m.m/m.cm);
m.G = m.Mx / m.cm ^2;
m.Oe = m.G * m.mu0;

%---- Measured constants ----
m.k = 1.3806504e-23 * m.J / m.K; %Boltzman constant
m.e = 1.602176487e-19 * m.C; %Electron charge
m.u = 1.660538782e-27 * m.kg; %Atomic mass unit
m.G_ = 6.67428e-11 * m.m ^3 / (m.s ^2 * m.kg); %Gravitational constant
m.h = 6.62606896e-34 * m.J * m.s; %Planck's constant

%---- Particle masses (u)----
m.u_e = 5.4857990943e-4;
m.u_p = 1.00727646677;
m.u_n = 1.00866491597;
m.u_D = 2.013553212724;
m.u_T = 3.0155007134;
m.u_He3 = 3.0149322473;
m.u_a = 4.001506179127;

%---- Mass Ratios ----
m.p_e = m.u_p / m.u_e;
m.D_e = m.u_D / m.u_e;
m.T_e = m.u_T / m.u_e;
m.He3_e = m.u_He3 / m.u_e;
m.a_e = m.u_a / m.u_e;

%---- Particle masses (kg)----
m.m_e = m.u_e * m.u;
m.m_p = m.u_p * m.u;
m.m_n = m.u_n * m.u;
m.m_D = m.u_D * m.u;
m.m_T = m.u_T * m.u;
m.m_He3 = m.u_He3 * m.u;
m.m_a = m.u_a * m.u;

%---- Lande g factors ----
m.g_e = -2.0023193043622;
m.g_p = 5.585694713;
m.g_n = -3.82608545;
m.g_D = 0.8574382308;
m.g_T = 5.957924896;

%---- Derived constants ----
m.hbar = m.h / ( 2 * pi); %Reduced Planck's constant;
m.eps0 = 1 / (m.mu0 * m.c ^2); %Permitivity of free space
m.eV = m.e * m.V; %Electron volt
m.m_eV = m.eV / m.c ^2; %Mass-energy equivalent of 1eV
m.h_eV = m.h / m.eV; %Planck's constant in eV
m.alpha = m.e ^2 / (4 * pi * m.eps0 * m.hbar * m.c); %Fine structure constant
m.a0 = m.eps0 * m.h ^2 / (pi * m.m_e * m.e ^2); % Bohr radius
m.r_e = m.e ^2 / (4 * pi * m.eps0 * m.m_e * m.c ^2); %Classical electron radius
m.l_c = m.h / (m.m_e * m.c); %Compton wavelength of electron
m.Ry = m.m_e * m.e ^4 / (8 * m.eps0 ^2 * m.c * m.h ^3); %Rydberg constant (m-1)
m.Ry_eV = m.Ry * m.c * m.h / m.eV; %Rydberg constant (eV)
m.sigma = pi ^2 * m.k ^4 / (60 * m.hbar ^3 * m.c ^2); %Stephan-Boltzmann constant
m.N_A = m.gm / m.u; %Avagadro's Number
m.R = m.N_A * m.k; %Ideal gas constant
m.n0 = m.atm / (m.k * m.T0); %Loschmidt's number (density at STP)
m.V0 = m.R * m.T0 / m.atm; %Molar volume at STP
m.mu_B = m.e * m.h / (4 * pi * m.m_e); %Bohr Magneton
m.mu_N = m.e * m.h / (4 * pi * m.m_p); %Nuclear Magneton

%---- Magnetic moments ----
m.mu_e = m.g_e * m.mu_B / 2;
m.mu_p = m.g_p * m.mu_N / 2;
m.mu_n = m.g_n * m.mu_N / 2;
m.mu_D = m.g_D * m.mu_N;
m.mu_T = m.g_T * m.mu_N / 2;

%---- Prefix Units ----
unit   = {'m';'g' ;'s';'K';'A';'mol';'eV';'L';'Hz';'J';'Pa';'W';'C';'V';'T';'F';'ohm';'siemens';'H';'G'};
unit0  = {'m';'gm';'s';'K';'A';'mol';'eV';'L';'Hz';'J';'Pa';'W';'C';'V';'T';'F';'ohm';'siemens';'H';'G'};
min    = {'f';'n' ;'f';'n';'n';'m'  ;'m' ;'m';'k' ;'m';'k' ;'p';'f';'n';'n';'p';'m'  ;'n'      ;'m';'m'};
max    = {'k';'m' ;'m';'m';'M';'k'  ;'T' ;'m';'T' ;'G';'G' ;'T';'m';'G';'m';'m';'G'  ;'k'      ;'u';'k'};
prefix = ['f'  ;'p'  ;'n' ;'u' ;'m' ;'k';'M';'G';'T' ];
exp    = {'-15';'-12';'-9';'-6';'-3';'3';'6';'9';'12'};

for j = 1:length(unit)
    min_i = find(prefix == min{j});
    max_i = find(prefix == max{j});
    for i = min_i:max_i
        eval(['m.' prefix(i) unit{j} ' = 1e' exp{i} '* m.' unit0{j} ';']);
    end
end

%---- Non SI Units ----
%---- length ----
m.ang = 1e-10 * m.m;
m.in = 2.54 * m.cm;
m.mil = 1e-3 * m.in;
m.ft = 12 * m.in;
m.yd = 3 * m.ft;
m.mi = 5280 * m.ft;

%---- Volume ----
m.cc = m.mL;
m.floz = 29.5735297 * m.cc;
m.pint = 16 * m.floz;
m.quart = 32 * m.floz;
m.gal = 128 * m.floz;

%---- mass ----
m.lb = 0.45359237 * m.kg;
m.oz = m.lb / 16;

%---- time ----
m.min = 60 * m.s;
m.hr = 60 * m.min;
m.day = 24 * m.hr;
m.yr = 365.242199 * m.day; 

%---- force ----
m.lbf = m.lb * m.g;

%---- energy ----
m.BTU = 1.0550559e3 * m.J;
m.kWh = m.kW * m.hr;
m.cal = 4.1868 * m.J;
m.kCal = 1e3 * m.cal;

%---- pressure ----
m.torr = m.atm / 760;
m.mtorr = 1e-3 * m.torr;
m.bar = 1e5 * m.Pa;
m.mbar = 1e-3 * m.bar;
m.psi = m.lbf / m.in^2;

%---- power ----
m.hp = 745.69987 * m.W;

%-------- CGS UNITS ------------------------------
%---- Fundamental units ----
c.cm = 1;
c.gm = 1;
c.s = 1;
c.K = 1;
c.stA = 1;
c.mol = 1;

%---- Mechanical MKS units in CGS ----
c.m = 1e2 * c.cm;
c.kg = 1e3 * c.gm;
c.N = c.kg * c.m / c.s^2;
c.J = c.N * c.m;
c.Pa = c.N / c.m^2;
c.W = c.J / c.s;

%---- Defined constants ----
c.c = 299792458 * c.m / c.s; %Speed of light
c.g = 9.80665 * c.m/c.s^2; %Standard gravitational acceleration on Earth
c.T0 = 273.15 * c.K; %0 degrees Celsius
c.atm = 101325 * c.Pa; %Standard atmospheric pressure
c.mu0 = 1; %Permeability of free space
c.eps0 = 1; %Permitivity of free space

%---- Derived units ----
c.L = 1e3 * c.cm ^3;
c.Hz = 1 / c.s;
c.dyne = c.gm * c.cm / c.s^2;
c.erg = c.dyne * c.cm;
c.stC =  c.stA * c.s;
c.stV = c.erg / c.stC;
c.Mx = c.stV * c.s;
c.G = c.Mx / c.cm ^2;
c.Oe = c.G * c.mu0;
c.poise = c.gm / c.cm / c.s;

%---- Electromagnetic MKS units in CGS
c.A = sqrt(c.cm/c.m) * c.c * c.stA;
c.C = c.A / c.s;
c.V = c.J / c.C;
c.Wb = c.V * c.s * c.c;
c.T = c.Wb / c.m ^2;
c.F = c.C / c.V;
c.ohm = c.V / c.A;
c.siemens = 1 / c.ohm;
c.H = c.V * c.s  / c.A;

%---- Measured constants ----
c.k = 1.3806504e-23 * c.J / c.K; %Boltzman constant
c.e = 1.602176487e-19 * c.C; %Electron charge
c.u = 1.660538782e-27 * c.kg; %Atomic mass unit
c.G_ = 6.67428e-11 * c.m ^3 / (c.s ^2 * c.kg); %Gravitational constant
c.h = 6.62606896e-34 * c.J * c.s; %Planck's constant

%---- Particle masses (u)----
c.u_e = 5.4857990943e-4;
c.u_p = 1.00727646677;
c.u_n = 1.00866491597;
c.u_D = 2.013553212724;
c.u_T = 3.0155007134;
c.u_He3 = 3.0149322473;
c.u_a = 4.001506179127;

%---- Mass Ratios ----
c.p_e = c.u_p / c.u_e;
c.D_e = c.u_D / c.u_e;
c.T_e = c.u_T / c.u_e;
c.He3_e = c.u_He3 / c.u_e;
c.a_e = c.u_a / c.u_e;

%---- Particle masses (kg)----
c.m_e = c.u_e * c.u;
c.m_p = c.u_p * c.u;
c.m_n = c.u_n * c.u;
c.m_D = c.u_D * c.u;
c.m_T = c.u_T * c.u;
c.m_He3 = c.u_He3 * c.u;
c.m_a = c.u_a * c.u;

%---- Lande g factors ----
c.g_e = -2.0023193043622;
c.g_p = 5.585694713;
c.g_n = -3.82608545;
c.g_D = 0.8574382308;
c.g_T = 5.957924896;

%---- Derived constants ----
c.hbar = c.h / ( 2 * pi); %Reduced Planck's constant;
c.eV = c.e * c.V; %Electron volt
c.m_eV = c.eV / c.c ^2; %Mass-energy equivalent of 1eV
c.h_eV = c.h / c.eV; %Planck's constant in eV
c.alpha = c.e ^2 / (c.hbar * c.c); %Fine structure constant
c.a0 = c.hbar ^2 / (c.m_e * c.e ^2); % Bohr radius
c.r_e = c.e ^2 / (c.m_e * c.c ^2); %Classical electron radius
c.l_c = c.h / (c.m_e * c.c); %Compton wavelength of electron
c.Ry = 2 * pi ^2 * c.m_e * c.e ^4 / (c.c * c.h ^3); %Rydberg constant (m-1)
c.Ry_eV = c.Ry * c.c * c.h / c.eV; %Rydberg constant (eV)
c.sigma = pi ^2 * c.k ^4 / (60 * c.hbar ^3 * c.c ^2); %Stephan-Boltzmann constant
c.N_A = c.gm / c.u; %Avagadro's Number
c.R = c.N_A * c.k; %Ideal gas constant
c.n0 = c.atm / (c.k * c.T0); %Loschmidt's number (density at STP)
c.V0 = c.R * c.T0 / c.atm; %Molar volume at STP
c.mu_B = c.e * c.hbar / (2 * c.m_e * c.c); %Bohr Magneton
c.mu_N = c.e * c.hbar / (2 * c.m_p * c.c); %Nuclear Magneton

%---- Magnetic moments ----
c.mu_e = c.g_e * c.mu_B / 2;
c.mu_p = c.g_p * c.mu_N / 2;
c.mu_n = c.g_n * c.mu_N / 2;
c.mu_D = c.g_D * c.mu_N;
c.mu_T = c.g_T * c.mu_N / 2;

%---- Prefix Units ----
unit   = {'m';'g' ;'s';'K';'A';'mol';'eV';'L';'Hz';'J';'Pa';'W';'C';'V';'T';'F';'ohm';'siemens';'H'};
unit0  = {'m';'gm';'s';'K';'A';'mol';'eV';'L';'Hz';'J';'Pa';'W';'C';'V';'T';'F';'ohm';'siemens';'H'};
min    = {'f';'n' ;'f';'n';'n';'m'  ;'m' ;'m';'k' ;'m';'k' ;'p';'f';'n';'n';'p';'m'  ;'n'      ;'m'};
max    = {'k';'m' ;'m';'m';'M';'k'  ;'T' ;'m';'T' ;'G';'G' ;'T';'m';'G';'m';'m';'G'  ;'k'      ;'u'};
prefix = ['f'  ;'p'  ;'n' ;'u' ;'m' ;'k';'M';'G';'T' ];
exp    = {'-15';'-12';'-9';'-6';'-3';'3';'6';'9';'12'};

for j = 1:length(unit)
    min_i = find(prefix == min{j});
    max_i = find(prefix == max{j});
    for i = min_i:max_i
        eval(['c.' prefix(i) unit{j} ' = 1e' exp{i} '* c.' unit0{j} ';']);
    end
end

%---- Non SI Units ----
%---- length ----
c.ang = 1e-10 * c.m;
c.in = 2.54 * c.cm;
c.mil = 1e-3 * c.in;
c.ft = 12 * c.in;
c.yd = 3 * c.ft;
c.mi = 5280 * c.ft;

%---- Volume ----
c.cc = c.mL;
c.floz = 29.5735297 * c.cc;
c.pint = 16 * c.floz;
c.quart = 32 * c.floz;
c.gal = 128 * c.floz;

%---- mass ----
c.lb = 0.45359237 * c.kg;
c.oz = c.lb / 16;

%---- time ----
c.min = 60 * c.s;
c.hr = 60 * c.min;
c.day = 24 * c.hr;
c.yr = 365.242199 * c.day; 

%---- force ----
c.lbf = c.lb * c.g;

%---- energy ----
c.BTU = 1.0550559e3 * c.J;
c.kWh = c.kW * c.hr;
c.cal = 4.1868 * c.J;
c.kCal = 1e3 * c.cal;

%---- pressure ----
c.torr = c.atm / 760;
c.mtorr = 1e-3 * c.torr;
c.bar = 1e5 * c.Pa;
c.mbar = 1e-3 * c.bar;
c.psi = c.lbf / c.in^2;

%---- power ----
c.hp = 745.69987 * c.W;