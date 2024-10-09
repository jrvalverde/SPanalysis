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
/*     */ class null
/*     */   extends DefaultTableModel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   null(Object[][] $anonymous0, Object[] $anonymous1) {
/*  95 */     super($anonymous0, $anonymous1);
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
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getColumnClass(int column) {
/* 112 */     if (getRowCount() > 0) {
/* 113 */       Object value = getValueAt(0, column);
/* 114 */       if (value != null) {
/* 115 */         return getValueAt(0, column).getClass();
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return super.getColumnClass(column);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ColorEditorSpot$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */