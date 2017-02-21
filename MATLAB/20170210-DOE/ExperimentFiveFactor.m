function [ Y ] = ExperimentFiveFactor( Start, End, maxRange, x )
%EXPERIMENTTWOFACTOR Summary of this function goes here
%   Detailed explanation goes here
Period = End - Start;
maxPoint = Start + Period*(maxRange)*rand() + Period*(1-maxRange)/2

end

