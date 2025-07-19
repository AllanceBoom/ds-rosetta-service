"""
GALECTIN-3 数据分析模块
专门用于分析GALECTIN-3蛋白在肝纤维化治疗中的表达和作用
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from scipy import stats
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score
import plotly.graph_objects as go
import plotly.express as px
from typing import Dict, List, Tuple, Optional
import logging

logger = logging.getLogger(__name__)


class Galectin3Analyzer:
    """GALECTIN-3蛋白表达分析器"""
    
    def __init__(self, threshold: float = 2.5):
        """
        初始化分析器
        
        Args:
            threshold: GALECTIN-3表达阈值，用于判断高低表达
        """
        self.threshold = threshold
        self.scaler = StandardScaler()
        
    def load_experiment_data(self, experiment_id: int) -> pd.DataFrame:
        """
        加载实验数据
        
        Args:
            experiment_id: 实验ID
            
        Returns:
            实验数据DataFrame
        """
        # 这里应该从数据库加载数据，暂时使用模拟数据
        logger.info(f"加载实验 {experiment_id} 的GALECTIN-3数据")
        
        # 模拟数据生成
        np.random.seed(42)
        n_samples = 100
        
        data = {
            'sample_id': range(1, n_samples + 1),
            'galectin3_level': np.random.normal(2.8, 0.8, n_samples),
            'treatment_group': np.random.choice(['control', 'treatment'], n_samples),
            'time_point': np.random.choice([0, 7, 14, 21, 28], n_samples),
            'fibrosis_stage': np.random.choice(['F0', 'F1', 'F2', 'F3', 'F4'], n_samples),
            'alt_level': np.random.normal(45, 15, n_samples),  # ALT水平
            'ast_level': np.random.normal(40, 12, n_samples),  # AST水平
            'age': np.random.randint(25, 70, n_samples),
            'gender': np.random.choice(['M', 'F'], n_samples)
        }
        
        df = pd.DataFrame(data)
        
        # 确保治疗组的GALECTIN-3水平有显著差异
        treatment_mask = df['treatment_group'] == 'treatment'
        df.loc[treatment_mask, 'galectin3_level'] *= 0.7  # 治疗组降低30%
        
        return df
    
    def analyze_expression_levels(self, data: pd.DataFrame) -> Dict:
        """
        分析GALECTIN-3表达水平
        
        Args:
            data: 实验数据
            
        Returns:
            分析结果字典
        """
        logger.info("分析GALECTIN-3表达水平")
        
        results = {}
        
        # 基础统计
        results['basic_stats'] = {
            'mean': data['galectin3_level'].mean(),
            'median': data['galectin3_level'].median(),
            'std': data['galectin3_level'].std(),
            'min': data['galectin3_level'].min(),
            'max': data['galectin3_level'].max(),
            'q25': data['galectin3_level'].quantile(0.25),
            'q75': data['galectin3_level'].quantile(0.75)
        }
        
        # 高低表达分类
        data['expression_level'] = data['galectin3_level'].apply(
            lambda x: 'high' if x > self.threshold else 'low'
        )
        
        results['expression_distribution'] = data['expression_level'].value_counts().to_dict()
        
        # 按治疗组分析
        group_stats = data.groupby('treatment_group')['galectin3_level'].agg([
            'count', 'mean', 'std', 'median'
        ]).round(3)
        
        results['group_comparison'] = group_stats.to_dict()
        
        # 统计检验
        control_group = data[data['treatment_group'] == 'control']['galectin3_level']
        treatment_group = data[data['treatment_group'] == 'treatment']['galectin3_level']
        
        # t检验
        t_stat, p_value = stats.ttest_ind(control_group, treatment_group)
        results['t_test'] = {
            't_statistic': t_stat,
            'p_value': p_value,
            'significant': p_value < 0.05
        }
        
        # Mann-Whitney U检验（非参数检验）
        u_stat, u_p_value = stats.mannwhitneyu(control_group, treatment_group, alternative='two-sided')
        results['mannwhitney_test'] = {
            'u_statistic': u_stat,
            'p_value': u_p_value,
            'significant': u_p_value < 0.05
        }
        
        return results
    
    def correlate_with_fibrosis(self, data: pd.DataFrame) -> Dict:
        """
        分析GALECTIN-3与纤维化程度的相关性
        
        Args:
            data: 实验数据
            
        Returns:
            相关性分析结果
        """
        logger.info("分析GALECTIN-3与纤维化的相关性")
        
        # 将纤维化分期转换为数值
        fibrosis_mapping = {'F0': 0, 'F1': 1, 'F2': 2, 'F3': 3, 'F4': 4}
        data['fibrosis_numeric'] = data['fibrosis_stage'].map(fibrosis_mapping)
        
        # 计算相关系数
        correlation_pearson = data['galectin3_level'].corr(data['fibrosis_numeric'])
        correlation_spearman = data['galectin3_level'].corr(data['fibrosis_numeric'], method='spearman')
        
        # 线性回归
        X = data[['galectin3_level']].values
        y = data['fibrosis_numeric'].values
        
        model = LinearRegression()
        model.fit(X, y)
        y_pred = model.predict(X)
        r2 = r2_score(y, y_pred)
        
        results = {
            'pearson_correlation': correlation_pearson,
            'spearman_correlation': correlation_spearman,
            'linear_regression': {
                'coefficient': model.coef_[0],
                'intercept': model.intercept_,
                'r2_score': r2
            }
        }
        
        # 按纤维化分期分组分析
        fibrosis_stats = data.groupby('fibrosis_stage')['galectin3_level'].agg([
            'count', 'mean', 'std'
        ]).round(3)
        
        results['fibrosis_stage_analysis'] = fibrosis_stats.to_dict()
        
        return results
    
    def time_series_analysis(self, data: pd.DataFrame) -> Dict:
        """
        时间序列分析
        
        Args:
            data: 实验数据
            
        Returns:
            时间序列分析结果
        """
        logger.info("进行GALECTIN-3时间序列分析")
        
        # 按时间点和治疗组分组
        time_series = data.groupby(['time_point', 'treatment_group'])['galectin3_level'].agg([
            'mean', 'std', 'count'
        ]).reset_index()
        
        results = {
            'time_series_data': time_series.to_dict('records')
        }
        
        # 计算变化率
        for group in ['control', 'treatment']:
            group_data = time_series[time_series['treatment_group'] == group].sort_values('time_point')
            if len(group_data) > 1:
                baseline = group_data.iloc[0]['mean']
                group_data['change_rate'] = (group_data['mean'] - baseline) / baseline * 100
                results[f'{group}_change_rates'] = group_data[['time_point', 'change_rate']].to_dict('records')
        
        return results
    
    def dose_response_analysis(self, data: pd.DataFrame, dose_column: str = None) -> Dict:
        """
        剂量-效应分析
        
        Args:
            data: 实验数据
            dose_column: 剂量列名
            
        Returns:
            剂量-效应分析结果
        """
        if dose_column and dose_column in data.columns:
            logger.info("进行剂量-效应分析")
            
            # 计算相关性
            dose_correlation = data[dose_column].corr(data['galectin3_level'])
            
            # 线性回归
            X = data[[dose_column]].values
            y = data['galectin3_level'].values
            
            model = LinearRegression()
            model.fit(X, y)
            r2 = r2_score(y, model.predict(X))
            
            results = {
                'dose_correlation': dose_correlation,
                'dose_regression': {
                    'coefficient': model.coef_[0],
                    'intercept': model.intercept_,
                    'r2_score': r2
                }
            }
            
            return results
        else:
            logger.warning("未提供有效的剂量数据列")
            return {'error': '未提供剂量数据'}
    
    def generate_visualization(self, data: pd.DataFrame, plot_type: str = 'distribution') -> go.Figure:
        """
        生成可视化图表
        
        Args:
            data: 实验数据
            plot_type: 图表类型 ('distribution', 'comparison', 'correlation', 'time_series')
            
        Returns:
            Plotly图表对象
        """
        logger.info(f"生成{plot_type}可视化图表")
        
        if plot_type == 'distribution':
            fig = px.histogram(
                data, 
                x='galectin3_level',
                nbins=20,
                title='GALECTIN-3表达水平分布',
                labels={'galectin3_level': 'GALECTIN-3水平', 'count': '频数'}
            )
            
        elif plot_type == 'comparison':
            fig = px.box(
                data,
                x='treatment_group',
                y='galectin3_level',
                title='治疗组vs对照组GALECTIN-3水平比较',
                labels={
                    'treatment_group': '组别',
                    'galectin3_level': 'GALECTIN-3水平'
                }
            )
            
        elif plot_type == 'correlation':
            # 将纤维化分期转换为数值
            fibrosis_mapping = {'F0': 0, 'F1': 1, 'F2': 2, 'F3': 3, 'F4': 4}
            data['fibrosis_numeric'] = data['fibrosis_stage'].map(fibrosis_mapping)
            
            fig = px.scatter(
                data,
                x='galectin3_level',
                y='fibrosis_numeric',
                color='treatment_group',
                title='GALECTIN-3水平与纤维化程度相关性',
                labels={
                    'galectin3_level': 'GALECTIN-3水平',
                    'fibrosis_numeric': '纤维化分期',
                    'treatment_group': '治疗组'
                }
            )
            
        elif plot_type == 'time_series':
            time_data = data.groupby(['time_point', 'treatment_group'])['galectin3_level'].mean().reset_index()
            
            fig = px.line(
                time_data,
                x='time_point',
                y='galectin3_level',
                color='treatment_group',
                title='GALECTIN-3水平时间变化趋势',
                labels={
                    'time_point': '时间点(天)',
                    'galectin3_level': 'GALECTIN-3水平',
                    'treatment_group': '治疗组'
                }
            )
            
        else:
            raise ValueError(f"不支持的图表类型: {plot_type}")
        
        fig.update_layout(
            template='plotly_white',
            font=dict(family="Arial, sans-serif", size=12),
            title_font_size=16
        )
        
        return fig
    
    def comprehensive_report(self, data: pd.DataFrame) -> Dict:
        """
        生成综合分析报告
        
        Args:
            data: 实验数据
            
        Returns:
            综合分析结果
        """
        logger.info("生成GALECTIN-3综合分析报告")
        
        report = {
            'summary': {
                'total_samples': len(data),
                'treatment_groups': data['treatment_group'].unique().tolist(),
                'time_points': sorted(data['time_point'].unique().tolist()),
                'fibrosis_stages': data['fibrosis_stage'].unique().tolist()
            },
            'expression_analysis': self.analyze_expression_levels(data),
            'fibrosis_correlation': self.correlate_with_fibrosis(data),
            'time_series': self.time_series_analysis(data)
        }
        
        # 添加关键发现
        expression_results = report['expression_analysis']
        if expression_results['t_test']['significant']:
            report['key_findings'] = [
                f"治疗组与对照组GALECTIN-3水平存在显著差异 (p={expression_results['t_test']['p_value']:.4f})",
                f"GALECTIN-3与纤维化程度相关性: r={report['fibrosis_correlation']['pearson_correlation']:.3f}"
            ]
        
        return report