function [ Y ] = ExperimentFiveFactor( Start, End, maxRange, x )
%EXPERIMENTTWOFACTOR Summary of this function goes here
%   Detailed explanation goes here
Period = End - Start;
maxPoint = Start + Period*(maxRange)*rand() + Period*(1-maxRange)/2;
% Y = 4.*exp(3/Period.*(-x+maxPoint))./((1+exp(3/Period.*(-x+maxPoint))).^2);
Y = 1./(1+exp(3/Period.*(-x+maxPoint)));
Y = 4*Y.*(1-Y);
end

