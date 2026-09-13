package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.movieticket.model.SeatBean;
import com.movieticket.util.DBConnection;

public class SeatManagementDAO {
    public boolean addSeat(SeatBean seat) {
        if (seat.getSeatId() == null || seat.getSeatId().isBlank()) seat.setSeatId(UUID.randomUUID().toString());
        String sql = "INSERT INTO seats (id, mall_id, seat_type_id, row_name, seat_number, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, seat.getSeatId()); p.setString(2, seat.getMallId()); p.setString(3, seat.getSeatTypeId());
            p.setString(4, seat.getRowName()); p.setInt(5, seat.getSeatNumber()); p.setBoolean(6, seat.isActive());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean updateSeat(SeatBean seat) {
        String sql = "UPDATE seats SET mall_id=?, seat_type_id=?, row_name=?, seat_number=?, status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, seat.getMallId()); p.setString(2, seat.getSeatTypeId()); p.setString(3, seat.getRowName());
            p.setInt(4, seat.getSeatNumber()); p.setBoolean(5, seat.isActive()); p.setString(6, seat.getSeatId());
            return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean deleteSeat(String id) {
        String sql = "DELETE FROM seats WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, id); return p.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<SeatBean> getAllSeats() {
        String sql = "SELECT s.id, s.mall_id, s.seat_type_id, s.row_name, s.seat_number, s.status, "
                + "m.name AS mall_name, st.type_name FROM seats s JOIN malls m ON m.id=s.mall_id "
                + "JOIN seat_types st ON st.id=s.seat_type_id ORDER BY m.name, s.row_name, s.seat_number";
        List<SeatBean> list = new ArrayList<>();
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()) {
            while(r.next()) list.add(map(r));
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    public List<SeatBean> getSeatsByTheatre(String mallId) {
        String sql = "SELECT s.id, s.mall_id, s.seat_type_id, s.row_name, s.seat_number, s.status, m.name AS mall_name, st.type_name "
                + "FROM seats s JOIN malls m ON m.id=s.mall_id JOIN seat_types st ON st.id=s.seat_type_id WHERE s.mall_id=? "
                + "ORDER BY s.row_name, s.seat_number";
        List<SeatBean> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,mallId); try(ResultSet r=p.executeQuery()){while(r.next()) list.add(map(r));}
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    public List<SeatBean> getSeatTypes() {
        List<SeatBean> list=new ArrayList<>();
        String sql="SELECT id,type_name FROM seat_types ORDER BY type_name";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            while(r.next()){ SeatBean s=new SeatBean(); s.setSeatTypeId(r.getString("id")); s.setTypeName(r.getString("type_name")); list.add(s); }
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    public SeatBean getSeatById(String id) {
        String sql = "SELECT s.id, s.mall_id, s.seat_type_id, s.row_name, s.seat_number, s.status, m.name AS mall_name, st.type_name "
                + "FROM seats s JOIN malls m ON m.id=s.mall_id JOIN seat_types st ON st.id=s.seat_type_id WHERE s.id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,id); try(ResultSet r=p.executeQuery()){ if(r.next()) return map(r); }
        } catch(Exception e){e.printStackTrace();}
        return null;
    }

    private SeatBean map(ResultSet r) throws Exception {
        SeatBean s=new SeatBean(); s.setSeatId(r.getString("id")); s.setMallId(r.getString("mall_id")); s.setSeatTypeId(r.getString("seat_type_id"));
        s.setRowName(r.getString("row_name")); s.setSeatNumber(r.getInt("seat_number")); s.setActive(r.getBoolean("status"));
        s.setMallName(r.getString("mall_name")); s.setTypeName(r.getString("type_name")); return s;
    }
}