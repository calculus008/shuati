Huahua's Summary
https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-dp-summary/

1.1
Input O(n)
dp[i] : optimal solution for a sub problem (A[1 -> i])
dp[i] only depends on const smaller problem
Time : O(n)
Space : O(n) -> O(1)

Template
dp = new int[n + 1]
for i = 1 to n
 dp[i] = f(dp[n - 1], dp[n - 2],...)
return dp[n]

[LE_70_Climbing_Stairs](src/leetcode/LE_70_Climbing_Stairs.java)\
[LE_198_House_Robber](src/leetcode/LE_198_House_Robber.java)\
[LE_746_Min_Cost_Climbing_Stairs](src/leetcode/LE_198_House_Robber.java)\
[LE_790_Domino_And_Tromino_Tiling](src/leetcode/LE_790_Domino_And_Tromino_Tiling.java)\
[LE_801_Minimum_Swaps_To_Make_Sequences_Increasing](src/leetcode/LE_801_Minimum_Swaps_To_Make_Sequences_Increasing)

---------------------------------------------------

LE_926_Flip_String_To_Monotone_Increasing
LE_901_Online_Stock_Span
LE_894_All_Possible_Full_Binary_Trees
LE_898_Bitwise_ORs_Of_Subarrays
LE_873_Length_Of_Longest_Fibonacci_Subsequence
LE_877_Stone_Game
LE_823_Binary_Trees_With_Factors
LE_813_Largest_Sum_Of_Averages
LE_410_Split_Array_Largest_Sum
LE_801_Minimum_Swaps_To_Make_Sequences_Increasing
LE_790_Domino_And_Tromino_Tiling
LE_712_Minimum_ASCII_Delete_Sum_For_Two_Strings
LE_115_Distinct_Subsequences

818
LE_879_Profitable_Schemes
LE_719_Find_Kth_Smallest_Pair_Distance
LE_546_Remove_Boxes

#Chessboard DP
LE_935_Knight_Dialer
LE_576_Out_Of_Boundary_Paths
LE_688_Knight_Probability_In_Chessboard

LE_787_Cheapest_Flights_Within_K_Stops

LE_121_Best_Time_To_Buy_And_Sell_Stock
LE_309_Best_Time_To_Buy_And_Sell_Stock_With_Cooldown
LE_416_Partition_Equal_Subset_Sum

LE_198_House_Robber
LE_740_Delete_And_Earn

LE_746_Min_Cost_Climbing_Stairs

LE_91_Decode_Ways
LE_639_Decode_Ways_II

LE_300_Longest_Increasing_Subsequence
LE_673_Number_Of_Longest_Increasing_Subsequence (Solution 1)
LE_674_Longest_Continuous_Increasing_Subsequence

LE_678_Valid_Parenthesis_String (Solution 2)
LE_871_Minimum_Number_Of_Refueling_Stops (Solution 2)

LE_664_Strange_Printer
LE_730_Count_Different_Palindromic_Subsequences
LE_516_Longest_Palindromic_Subsequence

LE_494_Target_Sum
LE_799_Champagne_Tower

#TSP
LE_943_Find_The_Shortest_Superstring
