package Interviews.Karat;

public class Manage_Meeting_Rooms {
    /**
     * 第一题找所有人空闲的时间，这是第二题
     *
     *  We are writing a tool to help users manage their calendars at a corporation.
     *  Given a list of meeting times and sizes, and a list of the available meeting
     *  room sizes, find a way to fit all of the meetings in a big enough room.
     *
     *  Sample input:
     *
     *  rooms = {
     *    "Phone Booth":     {"size":  2},
     *    "War Room":        {"size":  6},
     *    "Conference Room": {"size": 12},
     *  }
     *
     *  meetings1 = {
     *    "Standup": {"size":  4, "start": 1230, "end": 1300},
     *    "Scrum":   {"size":  6, "start": 1230, "end": 1330},
     *    "UAT":     {"size": 10, "start": 1300, "end": 1500},
     *  }
     *
     *  meetings2 = {
     *    "Manager 1:1": {"size": 2, "start":  900, "end": 1030},
     *    "Budget":      {"size": 4, "start":  900, "end": 1000},
     *    "Forecasting": {"size": 6, "start":  900, "end": 1100},
     *    "SVP 1:1":     {"size": 2, "start": 1000, "end": 1030},
     *    "UX Testing":  {"size": 4, "start": 1015, "end": 1130},
     *  }
     *
     *  Expected output:
     *
     *  assignRooms(meetings1, rooms)
     *   => { Standup: Conference Room
     *      Scrum: War Room
     *      UAT: Conference Room }
     *
     *  assignRooms(meetings2, rooms)
     *   => Impossible!
     *
     *  m = number of meetings
     *
     *  面试官说可以暴力遍历，贪心或者dp
     */
}
