/*     */ import javax.swing.table.DefaultTableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   null(Object[][] $anonymous0, Object[] $anonymous1) {
/*  96 */     super($anonymous0, $anonymous1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(int row, int col) {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getColumnClass(int column) {
/* 113 */     if (getRowCount() > 0) {
/* 114 */       Object value = getValueAt(0, column);
/* 115 */       if (value != null) {
/* 116 */         return getValueAt(0, column).getClass();
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return super.getColumnClass(column);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ColorEditorTrack$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */